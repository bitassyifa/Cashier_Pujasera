package com.projectassyifa.cashier_pujasera.data.income.repo

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.cashier_pujasera.data.income.api.IncomeAPI
import com.projectassyifa.cashier_pujasera.data.income.model.IncomeModel
import com.projectassyifa.cashier_pujasera.data.member.model.MemberModel
import com.projectassyifa.cashier_pujasera.screen.alert.CekWifi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class IncomeRepo @Inject constructor(val incomeAPI: IncomeAPI){
    var income_report : MutableLiveData<IncomeModel> = MutableLiveData()

    fun income(incomeModel: IncomeModel,context: Context){
        incomeAPI.income(incomeModel).enqueue(object : Callback<IncomeModel> {
            override fun onResponse(call: Call<IncomeModel>, response: Response<IncomeModel>) {
                val res = response.body()

                val gson = Gson()
                val listMember: Type = object : TypeToken<IncomeModel>() {}.type
                val Output: IncomeModel =
                    gson.fromJson(gson.toJson(res), listMember)
                income_report.value = Output

                println("DATA INCOME ${income_report.value}")
                println("DATA INCOME res $res")
            
            }

            override fun onFailure(call: Call<IncomeModel>, t: Throwable) {
                t.printStackTrace()
                val loading = CekWifi(context as Activity)
                loading.startLoading()
                val handler = Handler()
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        loading.isDismiss()
                    }

                }, 3000)
                Toast.makeText(
                    context,
                    "Error : ${t.printStackTrace()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}