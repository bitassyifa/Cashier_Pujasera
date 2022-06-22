package com.projectassyifa.cashier_pujasera.screen.cash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.application.isradeleon.thermalprinter.ConnectBluetoothActivity
import com.application.isradeleon.thermalprinter.models.PrintAlignment
import com.application.isradeleon.thermalprinter.models.PrintFont
import com.application.isradeleon.thermalprinter.models.ThermalPrinter
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.data.sendReport.model.SendReportModel
import com.projectassyifa.cashier_pujasera.data.sendReport.vm.SendReportVM
import com.projectassyifa.cashier_pujasera.screen.alert.Done
import kotlinx.android.synthetic.main.activity_cash_payment.*
import kotlinx.android.synthetic.main.activity_fadipay.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CashPaymentActivity : AppCompatActivity() {
    var total_belanja : String ? = null
    var dataLogin: SharedPreferences? = null
    var sum : Int =0
    @Inject
    lateinit var sendReportVM: SendReportVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_payment)
        (applicationContext as MyApplication).applicationComponent.inject(this)
        dataLogin = this.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
        btn_clear_cash.setOnClickListener {
            harga_cash.setText("")
            total_cash.setText("0")
            sum =0
        }
        btn_save_cash.setOnClickListener {
            if (sum == 0) {

                Toast.makeText(
                    this,
                    "Total masih 0!,tidak bisa bertransaksi",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val merchant = dataLogin?.getString(
                    getString(R.string.merchant),
                    getString(R.string.default_value)
                )
                val username = dataLogin?.getString(
                    getString(R.string.username),
                    getString(R.string.default_value)
                )
                val server = dataLogin?.getString(
                    getString(R.string.server_pjs),
                    getString(R.string.default_value)
                )

                val dataSend = SendReportModel(
                    server = server.toString(),
                    db = merchant.toString(),
                    jumlah = sum.toString(),
                    created_by = username!!
                )
                println("DATA CASH ${dataSend.server}, ${dataSend.db}, ${dataSend.jumlah}, ${dataSend.created_by}")
                sendReportVM.data_response?.observe(this, androidx.lifecycle.Observer {
                    if (it.success == true) {
                        val loading = Done(this)
                        loading.startLoading()
                        val handler = Handler()
                        handler.postDelayed(object : Runnable {
                            override fun run() {
                                loading.isDismiss()
                            }

                        }, 3000)

                        harga_cash.setText("")
                        total_cash.setText("0")
                        sum = 0
                    }
                })
                sendReportVM.reporting(dataSend, this@CashPaymentActivity)
            }
        }
        btn_print_cash.setOnClickListener {
            startActivityForResult(
                Intent(this, ConnectBluetoothActivity::class.java),
                ConnectBluetoothActivity.CONNECT_BLUETOOTH
            )
        }

        tambah_cash.setOnClickListener {
            sum = (harga_cash.value + sum).toInt()


            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            total_cash.setText(formatRupiah.format(sum))
            total_belanja = total_cash.text.toString()
            harga_cash.setText("0")
        }

    }
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
        val merchant = dataLogin?.getString(
            getString(R.string.merchant),
            getString(R.string.default_value)
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val timeNow = sdf.format(Date())
        if(resultCode == Activity.RESULT_OK && requestCode == ConnectBluetoothActivity.CONNECT_BLUETOOTH){
            // ThermalPrinter is ready
            ThermalPrinter.instance

                .write("$merchant", PrintAlignment.CENTER, PrintFont.LARGE)
                .writeImage(BitmapFactory.decodeResource(getResources(), R.drawable.lgloundry))
                .fillLineWith('-')
                .write("")

                .write("Total Belanja : $total_belanja  ", PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("")
                .fillLineWith('-')
                .write("Kasir  : $nama_kasir  ", PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("Waktu  : $timeNow  ", PrintAlignment.LEFT, PrintFont.NORMAL)
                .write("Metode : CASH PAYMENT ", PrintAlignment.LEFT, PrintFont.NORMAL)
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
}