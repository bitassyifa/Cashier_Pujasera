package com.projectassyifa.cashier_pujasera.screen.report_server

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.data.sendReport.adapter.AdapterReport
import com.projectassyifa.cashier_pujasera.data.sendReport.model.ReportModel
import com.projectassyifa.cashier_pujasera.data.sendReport.vm.ReportVM
import kotlinx.android.synthetic.main.activity_income.*
import kotlinx.android.synthetic.main.activity_report.*
import kotlinx.android.synthetic.main.activity_report_server.*
import kotlinx.android.synthetic.main.activity_report_server.tgl1
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ReportServerActivity : AppCompatActivity() {
    var dataLogin: SharedPreferences? = null
    @Inject
    lateinit var reportVM : ReportVM
    lateinit var adapterReport :AdapterReport
    var calender = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_server)
        (applicationContext as MyApplication).applicationComponent.inject(this)
        dataLogin = this.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )

        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        tgl1.text = currentDate
        val merchant = dataLogin?.getString(
            getString(R.string.merchant),
            getString(R.string.default_value)
        )
        val server= dataLogin?.getString(
            getString(R.string.server_pjs),
            getString(R.string.default_value)
        )
        var data = ReportModel(
            created_by = username.toString(),
            created_date = currentDate,
            server = server.toString(),
            db = merchant.toString()

        )
        report_server.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        reportVM.report_server?.observe(this, Observer {
            adapterReport =AdapterReport(it,this)
            report_server.adapter=adapterReport
        })
        reportVM.report(data,this)

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
                DatePickerDialog(this@ReportServerActivity,
                    dateStart,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun updateTgl() {
            val formatTgl = "dd-MM-yyyy"
            val sdf = SimpleDateFormat(formatTgl,Locale.US)
        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )

        val merchant = dataLogin?.getString(
            getString(R.string.merchant),
            getString(R.string.default_value)
        )
        val server= dataLogin?.getString(
            getString(R.string.server_pjs),
            getString(R.string.default_value)
        )

        tgl1.text = sdf.format(calender.time)
        val data = ReportModel(
            created_by = username.toString(),
            created_date = sdf.format(calender.time),
            server = server.toString(),
            db = merchant.toString()

        )
        report_server.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        reportVM.report_server?.observe(this, Observer {
            adapterReport =AdapterReport(it,this)
            report_server.adapter=adapterReport
        })

        reportVM.report(data,this)
    }
}