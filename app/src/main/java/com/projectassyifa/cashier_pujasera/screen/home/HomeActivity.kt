package com.projectassyifa.cashier_pujasera.screen.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.room.SaleDB
import com.projectassyifa.cashier_pujasera.screen.fadipay.FadipayActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    val db by lazy { SaleDB(this) }

    private var backpressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


    }

    override fun onStart() {
        super.onStart()
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())
        CoroutineScope(Dispatchers.IO).launch {
            val sales = db.saleDao().getSale(currentDate)


        }
    }
    override fun onBackPressed() {
        if (backpressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity()

        } else {
            Toast.makeText(
                applicationContext,
                "Tekan kembali sekali lagi untuk keluar",
                Toast.LENGTH_SHORT
            ).show()
        }
        backpressedTime = System.currentTimeMillis()

    }
}