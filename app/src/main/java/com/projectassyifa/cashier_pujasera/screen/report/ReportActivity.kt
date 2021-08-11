package com.projectassyifa.cashier_pujasera.screen.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.room.SaleDB
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportActivity : AppCompatActivity() {

    val db by lazy { SaleDB(this) }
    lateinit var reportAdapter : ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        setupDataReport()
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val penjualan = db.saleDao().getSale()
            println("INI DATABASE $penjualan")
            withContext(Dispatchers.Main){
                reportAdapter.setData(penjualan)
            }
        }
    }
    private fun setupDataReport() {
        reportAdapter = ReportAdapter(arrayListOf(),this)
        report.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = reportAdapter
        }

    }
}