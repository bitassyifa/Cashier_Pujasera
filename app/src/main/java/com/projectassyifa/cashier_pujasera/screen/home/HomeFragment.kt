package com.projectassyifa.cashier_pujasera.screen.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.screen.cash.CashPaymentActivity
import com.projectassyifa.cashier_pujasera.screen.fadipay.FadipayActivity
import com.projectassyifa.cashier_pujasera.screen.income.IncomeActivity
import com.projectassyifa.cashier_pujasera.screen.login.Login_Activity
import com.projectassyifa.cashier_pujasera.screen.report.ReportActivity
import com.projectassyifa.cashier_pujasera.screen.report_server.ReportServerActivity
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(),View.OnClickListener {
    var dataLogin: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataLogin = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_logout.setOnClickListener(this)

        menu1.setOnClickListener {
            var move = Intent(this.context, FadipayActivity::class.java)
            startActivity(move)
        }

        menu2.setOnClickListener {
            startActivity(Intent(this.context,CashPaymentActivity::class.java))
        }
        menu5.setOnClickListener {
            startActivity(Intent(this.context,ReportServerActivity::class.java))
        }
        menu6.setOnClickListener {
            startActivity(Intent(this.context,IncomeActivity::class.java))
        }

        val pjs= dataLogin?.getString(
            getString(R.string.pjs),
            getString(R.string.default_value)
        )
        val nama= dataLogin?.getString(
            getString(R.string.nama_pegawai),
            getString(R.string.default_value)
        )
        val server= dataLogin?.getString(
            getString(R.string.server_pjs),
            getString(R.string.default_value)
        )
        pjsr.text = pjs
        server_pjs.text= server
        nama_kasir.text = "ahlan, $nama"
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_logout -> {
                println("BTN LOGOUT")
                // Initialize a new instance of
                val builder = AlertDialog.Builder(activity)

                // Set the alert dialog title
                builder.setTitle("Konfirmasi Logout")

                // Display a message on alert dialog
                builder.setMessage("Apakah anda yakin, Anda ingin Keluar Akun sekarang?")

                // Set a positive button and its click listener on alert dialog
                builder.setPositiveButton("Ya") { dialog, which ->
                    // Do something when user press the positive button
                    with(dataLogin?.edit()) {
                        this?.clear()
                        this?.apply()
                        Intent(getContext(), Login_Activity::class.java).apply {
                            addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                        }.also { startActivity(it) }
                    }
                }


// Display a negative button on alert dialog
                builder.setNegativeButton("Tidak"){dialog,which ->
//                    Toast.makeText(activity,"Anda tidak yakin.", Toast.LENGTH_SHORT).show()
                }


// Display a neutral button on alert dialog
//                        builder.setNeutralButton("Cancel"){_,_ ->
//                            Toast.makeText(activity,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
//                        }

// Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

// Display the alert dialog on app interface
                dialog.show()
            }
        }
    }


}