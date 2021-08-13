package com.projectassyifa.cashier_pujasera.screen.report

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.data.sendReport.model.SendReportModel
import com.projectassyifa.cashier_pujasera.data.sendReport.vm.SendReportVM
import com.projectassyifa.cashier_pujasera.room.SaleDB
import com.projectassyifa.cashier_pujasera.room.SaleModel
import com.projectassyifa.cashier_pujasera.screen.home.HomeActivity
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ReportActivity : AppCompatActivity() {
    var calender = Calendar.getInstance()
    val db by lazy { SaleDB(this) }


    @Inject
    lateinit var sendReportVM: SendReportVM

    lateinit var reportAdapter : ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        (applicationContext as MyApplication).applicationComponent.inject(this)
        refreshApp()
        reportAdapter = ReportAdapter(arrayListOf(), this)
        report.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = reportAdapter


//        setupDataReport()
            send_all.setOnClickListener {

                val sdf = SimpleDateFormat("dd-M-yyyy")
                val currentDate = sdf.format(Date())
                var data = reportAdapter.listReport
                data.forEach {
                    if (it.status_data == "OPEN"){
                        println(it.id_penjualan)
                        CoroutineScope(Dispatchers.IO).launch {
                            db.saleDao().updateSale(
                                SaleModel(it.id_penjualan,it.nama_pelanggan,it.no_kartu,it.total_harga,
                                    currentDate.toString(),it.db,it.server,it.created_by,"CLOSE")
                            )

                        }
//
                        val dataSend = SendReportModel(
                            server = it.server,
                            db = it.db,
                            jumlah = it.total_harga.toString(),
                            id_pelanggan = it.no_kartu,
                            nama_pelanggan = it.nama_pelanggan,
                            created_by = it.created_by
                        )
//            println("DATA JUMLAH ${dataSend.jumlah},DATA SERVER ${dataSend.server},DATA db ${dataSend.db},DATA id plnggn ${dataSend.id_pelanggan},DATA namapelanggan ${dataSend.nama_pelanggan},DATA created${dataSend.created_by}")
                        sendReportVM.reporting(dataSend,this@ReportActivity)

                        startActivity(Intent(this.context, HomeActivity::class.java))
                    }
                }

            }
        }
        //tgl awal
        val dateStart = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calender.set(Calendar.YEAR,year)
                calender.set(Calendar.MONTH,month)
                calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                updateTgl()
            }
        }

        tgl1.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                DatePickerDialog(this@ReportActivity,
                    dateStart,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun refreshApp() {
        refreshLayout.setOnRefreshListener {
            Toast.makeText(this,"Refresh", Toast.LENGTH_SHORT).show()
            finish();
//            reportAdapter = ReportAdapter(arrayListOf(), this)
//            report.apply {
//                layoutManager = LinearLayoutManager(applicationContext)
//                adapter = reportAdapter
//            }
            startActivity(getIntent());

        }
    }

    private fun updateTgl() {
        val formatTgl = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(formatTgl,Locale.US)
        tgl1.text = sdf.format(calender.time)
//        CoroutineScope(Dispatchers.IO).launch {
//            val penjualan = db.saleDao().getSale(sdf.format(calender.time))
//            println("INI DATABASE $penjualan")
//            withContext(Dispatchers.Main){
//                reportAdapter.setData(penjualan)
//            }
//        }


    }

    override fun onStart() {
        super.onStart()
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        CoroutineScope(Dispatchers.IO).launch {
            val penjualan = db.saleDao().getSale(currentDate)
            println("INI DATABASE $penjualan")
            withContext(Dispatchers.Main){
                reportAdapter.setData(penjualan)
            }
        }
    }
//    private fun setupDataReport() {
//        reportAdapter = ReportAdapter(arrayListOf(),this)
//        report.apply {
//            layoutManager = LinearLayoutManager(applicationContext)
//            adapter = reportAdapter
//        }
//
//    }
}