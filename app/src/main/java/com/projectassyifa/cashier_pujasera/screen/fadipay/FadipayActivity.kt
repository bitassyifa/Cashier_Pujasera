package com.projectassyifa.cashier_pujasera.screen.fadipay

import android.app.Activity
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.nfc.FormatException
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.application.isradeleon.thermalprinter.ConnectBluetoothActivity
import com.application.isradeleon.thermalprinter.models.PrintAlignment
import com.application.isradeleon.thermalprinter.models.PrintFont
import com.application.isradeleon.thermalprinter.models.ThermalPrinter
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.data.login.model.UserLoginModel
import com.projectassyifa.cashier_pujasera.data.member.model.MemberModel
import com.projectassyifa.cashier_pujasera.data.member.vm.CekPinVM
import com.projectassyifa.cashier_pujasera.data.member.vm.MemberVM
import com.projectassyifa.cashier_pujasera.data.sendReport.model.SendReportModel
import com.projectassyifa.cashier_pujasera.data.sendReport.vm.SendReportVM
import com.projectassyifa.cashier_pujasera.room.SaleDB
import com.projectassyifa.cashier_pujasera.room.SaleModel
import com.projectassyifa.cashier_pujasera.screen.alert.Done
import com.projectassyifa.cashier_pujasera.screen.alert.Failed
import com.projectassyifa.cashier_pujasera.screen.fadipay.nfc.WritableTag
import com.projectassyifa.cashier_pujasera.screen.home.HomeActivity
import kotlinx.android.synthetic.main.activity_fadipay.*
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FadipayActivity : AppCompatActivity(), View.OnClickListener {
    private var adapter: NfcAdapter? = null

    var tag: WritableTag? = null
//    var tagId: Int = 0
var tagId: String? =null
    var dataLogin: SharedPreferences? = null
    var nama_pelanggan : String ? = null
    var nomor_kartu : String? = null
    var total_belanja : String ? = null
    var saldo_fadi : Int = 0
    var sum : Int =0
    var status_pin  : Boolean = false


    val db by lazy { SaleDB(this) }

    @Inject
    lateinit var sendReportVM: SendReportVM

    @Inject
    lateinit var memberVM :MemberVM

    @Inject
    lateinit var cekpinVM :CekPinVM


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fadipay)
        (applicationContext as MyApplication).applicationComponent.inject(this)
        dataLogin = this.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
        initNfcAdapter()
        val pjs= dataLogin?.getString(
            getString(R.string.pjs),
            getString(R.string.default_value)
        )

        btn_save.isVisible = false
        btn_print.isVisible = false




        cekpin1.setOnClickListener {

            if (TextUtils.isEmpty(pin_fadi.getText())) {


                pin_fadi.setError("Masukan Pin!")
            } else {
                cekpinVM.responseData?.observe(this, androidx.lifecycle.Observer {

                    status_pin = it.status
                    if (status_pin == true) {

                        btn_save.isVisible = true
                        btn_print.isVisible = true
                    } else {
                        pin_fadi.setText(" ")
                    }

                })
                val dataCekpin = MemberModel(

                    cek_pin = pin_fadi.text.toString(),
                    no_kartu = nomor_kartu!!
                )

                cekpinVM.member(dataCekpin, this)
            }
        }
        btn_save.setOnClickListener{
   val pjs= dataLogin?.getString(
                getString(R.string.pjs),
                getString(R.string.default_value)
            )
            val username = dataLogin?.getString(
                getString(R.string.username),
                getString(R.string.default_value)
            )
            if (nama_pelanggan == null ){
                Toast.makeText(
                    this,
                    "DATA TRANSAKSI BELUM LENGKAP!!",
                    Toast.LENGTH_SHORT
                ).show()
            } else  {
                if (saldo_fadi < sum) {
                    Toast.makeText(
                        this,
                        "Saldo Tidak Mencukupi!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val sdf = SimpleDateFormat("dd-M-yyyy")
                    val currentDate = sdf.format(Date())
////                CoroutineScope(Dispatchers.IO).launch {
////                    db.saleDao().addSale(
////                        SaleModel(0,nama_pelanggan.toString(),nomor_kartu.toString(),sum,
////                            currentDate.toString(),pjs.toString(),"WANAREJA",username.toString(),"OPEN")
////                    )
////
////                }
                    val server= dataLogin?.getString(
                        getString(R.string.server_pjs),
                        getString(R.string.default_value)
                    )

                    val dataSend = SendReportModel(
                        server = server.toString(),
                        db = pjs.toString(),
                        jumlah = sum.toString(),
                        id_pelanggan = nomor_kartu!!,
                        nama_pelanggan = nama_pelanggan!!,
                        created_by = username!!
                    )
//            println("DATA JUMLAH ${dataSend.jumlah},DATA SERVER ${dataSend.server},DATA db ${dataSend.db},DATA id plnggn ${dataSend.id_pelanggan},DATA namapelanggan ${dataSend.nama_pelanggan},DATA created${dataSend.created_by}")
                    sendReportVM.data_response?.observe(this, Observer {
                        if (it.kirim_fadi == true){

                    val loading = Done(this)
                    loading.startLoading()
                    val handler = Handler()
                    handler.postDelayed(object :Runnable{
                        override fun run() {
                            loading.isDismiss()
                        }

                    },3000)
                            Nama.setText("")
                            No_kartu.setText("")
                            saldo.setText("")
                            pin_fadi.setText("")
                            Harga.setText("")
                            total.setText("0")
                            sum =0
                            status_pin = false
                            btn_save.isVisible = false
                            btn_print.isVisible = false
                        }
                    })
                    sendReportVM.reporting(dataSend,this@FadipayActivity)




//
//                startActivityForResult(
//                    Intent(this, ConnectBluetoothActivity::class.java),
//                    ConnectBluetoothActivity.CONNECT_BLUETOOTH
//                )

                }
            }


        }

        btn_print.setOnClickListener (this)

//        initViews()
        tambah.setOnClickListener {

//                total.text =
//                    (Harga.value + total.text.toString().toInt() + 0).toString()
             sum = (Harga.value + sum).toInt()


            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            total.setText(formatRupiah.format(sum))
            total_belanja = total.text.toString()
            Harga.setText("0")
        }
        btn_clear.setOnClickListener {
            Harga.setText("")
            total.setText("0")
            sum =0
        }



        }



    //print
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val nama_kasir = dataLogin?.getString(
            getString(R.string.nama_pegawai),
            getString(R.string.default_value)
        )
        val pjs= dataLogin?.getString(
            getString(R.string.pjs),
            getString(R.string.default_value)
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val timeNow = sdf.format(Date())
        if(resultCode == Activity.RESULT_OK && requestCode == ConnectBluetoothActivity.CONNECT_BLUETOOTH){
            // ThermalPrinter is ready
            ThermalPrinter.instance

                .write("$pjs", PrintAlignment.CENTER, PrintFont.LARGE)
                .writeImage(BitmapFactory.decodeResource(getResources(), R.drawable.lg_cashier_p))
                .fillLineWith('-')
                .write("")
                .write("Nama Pelanggan : $nama_pelanggan  ",PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("Total Belanja : $total_belanja  ",PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("")
                .fillLineWith('-')

                .write("Kasir  : $nama_kasir  ",PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("Waktu  : $timeNow  ",PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("Metode : FADIPAY PAYMENT ",PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("")
                .fillLineWith('-')
                .write("### LUNAS ###", PrintAlignment.CENTER, PrintFont.LARGE)
                .write("")
                .fillLineWith('-')
                .write("Terimakasih", PrintAlignment.CENTER, PrintFont.LARGE)
                .write("")

//                .writeImage(BitmapFactory.decodeResource(getResources(), R.drawable.lg_kawarung))
                .print()

        }
    }

    private fun initNfcAdapter() {
        val nfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        adapter = nfcManager.defaultAdapter
    }



    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    override fun onPause() {
        disableNfcForegroundDispatch()
        super.onPause()
    }

    private fun enableNfcForegroundDispatch() {
        try {
            val intent = Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val nfcPendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            adapter?.enableForegroundDispatch(this, nfcPendingIntent, null, null)
        } catch (ex: IllegalStateException) {
            Log.e(getTag(), "Error enabling NFC foreground dispatch", ex)
        }
    }

    private fun disableNfcForegroundDispatch() {
        try {
            adapter?.disableForegroundDispatch(this)
        } catch (ex: IllegalStateException) {
            Log.e(getTag(), "Error disabling NFC foreground dispatch", ex)
        }
    }

    private fun getTag() = "FadipayActivity"

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val tagFromIntent = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        try {
            tag = tagFromIntent?.let { WritableTag(it) }
        } catch (e: FormatException) {
            Log.e(getTag(), "Unsupported tag tapped", e)
            return
        }
        tagId = tag?.tagId.toString()
//        showToast("Tag tapped: $tagId")
        println("NOMOR KARTU $tagId")

        //data member
        memberVM.responseData?.observe(this, Observer {
//            Nama.text = "Apinchocs"
            Nama.text = it.nama
            println(" no kartu ${it.no_kartu}")
            No_kartu.text = it.no_kartu
            saldo.text = it.saldo.toString()
            saldo_fadi = it.saldo
            nama_pelanggan = it.nama
            nomor_kartu = it.no_kartu

        })
        val data = MemberModel(
            no_kartu = tagId.toString()
        )
        memberVM.member(data,this)

    }

    override fun onClick(v: View?) {
        when(v) {
//            btn_save -> {
//                val pjs= dataLogin?.getString(
//                    getString(R.string.pjs),
//                    getString(R.string.default_value)
//                )
//                val username = dataLogin?.getString(
//                    getString(R.string.username),
//                    getString(R.string.default_value)
//                )
//                if (nama_pelanggan == null ){
//                    Toast.makeText(
//                        this,
//                        "DATA TRANSAKSI BELUM LENGKAP!!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else  {
//                    if (saldo_fadi < sum) {
//                        Toast.makeText(
//                            this,
//                            "Saldo Tidak Mencukupi!",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        val sdf = SimpleDateFormat("dd-M-yyyy")
//                        val currentDate = sdf.format(Date())
////                CoroutineScope(Dispatchers.IO).launch {
////                    db.saleDao().addSale(
////                        SaleModel(0,nama_pelanggan.toString(),nomor_kartu.toString(),sum,
////                            currentDate.toString(),pjs.toString(),"WANAREJA",username.toString(),"OPEN")
////                    )
////
////                }
//                        val dataSend = SendReportModel(
//                            server = "WANAREJA",
//                            db = pjs.toString(),
//                            jumlah = sum.toString(),
//                            id_pelanggan = nomor_kartu!!,
//                            nama_pelanggan = nama_pelanggan!!,
//                            created_by = username!!
//                        )
////            println("DATA JUMLAH ${dataSend.jumlah},DATA SERVER ${dataSend.server},DATA db ${dataSend.db},DATA id plnggn ${dataSend.id_pelanggan},DATA namapelanggan ${dataSend.nama_pelanggan},DATA created${dataSend.created_by}")
//                        sendReportVM.reporting(dataSend,this@FadipayActivity)
//
//                        finish()
//                        startActivity(Intent(this,HomeActivity::class.java))
////                startActivityForResult(
////                    Intent(this, ConnectBluetoothActivity::class.java),
////                    ConnectBluetoothActivity.CONNECT_BLUETOOTH
////                )
//                    }
//                }
//
//
//            }
            btn_print ->{
                                startActivityForResult(
                    Intent(this, ConnectBluetoothActivity::class.java),
                    ConnectBluetoothActivity.CONNECT_BLUETOOTH
                )
            }
        }
    }
    }


