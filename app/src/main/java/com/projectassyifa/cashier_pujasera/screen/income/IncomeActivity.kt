package com.projectassyifa.cashier_pujasera.screen.income

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.bumptech.glide.Glide
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.data.income.model.IncomeModel
import com.projectassyifa.cashier_pujasera.data.income.vm.IncomeVM
//import com.projectassyifa.cashier_pujasera.data.sendReport.model.SendReportModel
import kotlinx.android.synthetic.main.activity_income.*
import kotlinx.android.synthetic.main.activity_income.nama_kasir
import kotlinx.android.synthetic.main.activity_income.tgl1
import kotlinx.android.synthetic.main.activity_report_server.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class IncomeActivity : AppCompatActivity() {

    @Inject
    lateinit var incomeVM: IncomeVM

    var calender = Calendar.getInstance()
    var dataLogin: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)
        (applicationContext as MyApplication).applicationComponent.inject(this)

        dataLogin = this.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )

        val username = dataLogin?.getString(
            getString(R.string.username),
            getString(R.string.default_value)
        )
        val nama= dataLogin?.getString(
            getString(R.string.nama_pegawai),
            getString(R.string.default_value)
        )
        val server = dataLogin?.getString(
            getString(R.string.server_pjs),
            getString(R.string.default_value)
        )
        val pjs = dataLogin?.getString(
            getString(R.string.pjs),
            getString(R.string.default_value)
        )
        val merchant = dataLogin?.getString(
            getString(R.string.merchant),
            getString(R.string.default_value)
        )
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        tgl1.text = currentDate
        nama_kasir.text = nama
        pujsr.text = merchant
        server_pujasera.text=server

        val foto = dataLogin?.getString(
            getString(R.string.foto),
            getString(R.string.default_value)
        )
        var linkFoto= "http://202.62.9.138:1234/hrd/php/foto/$foto"
        Glide
            .with(this)
            .load(linkFoto)
            .fitCenter()
            .placeholder(R.drawable.ic_profil)
            .into(profil_image);

        val dataSend = IncomeModel(
            server = server.toString(),
            db = merchant.toString(),
            created_by = username!!,
            created_date = currentDate
        )
        incomeVM.income_report?.observe(this, androidx.lifecycle.Observer {
            saldo_fadi.text = "Income : ${it.total}"
        })
//        println("DATA ${dataSend.server},${dataSend.db},${dataSend.created_by},${dataSend.created_date}")
        incomeVM.income(dataSend,this@IncomeActivity)

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
                DatePickerDialog(this@IncomeActivity,
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
        val dataSend = IncomeModel(
            server = server.toString(),
            db = merchant.toString(),
            created_by = username!!,
            created_date = sdf.format(calender.time)
        )
        incomeVM.income_report?.observe(this, androidx.lifecycle.Observer {
            saldo_fadi.text = "Income : ${it.total}"
        })
//        println("DATA ${dataSend.server},${dataSend.db},${dataSend.created_by},${dataSend.created_date}")
        incomeVM.income(dataSend,this@IncomeActivity)
    }


}