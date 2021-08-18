package com.projectassyifa.cashier_pujasera.data.sendReport.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.cashier_pujasera.data.sendReport.api.ReportAPI
import com.projectassyifa.cashier_pujasera.data.sendReport.model.ReportModel
import com.projectassyifa.cashier_pujasera.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class ReportRepo @Inject constructor(val reportAPI: ReportAPI) {
    var report_server : MutableLiveData<List<ReportModel>> = MutableLiveData()
    var not_found : String? = null
    fun report(reportModel: ReportModel, context: Context){
        reportAPI.report(reportModel).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                val dataReport = res?.data
                println("STATUS ${res?.status}")
                if (res?.status == false){
                    Toast.makeText(
                        context,
                        "Data Tidak Di Temukan",
                        Toast.LENGTH_SHORT
                    ).show()
                    val gson = Gson()
                    val listData : Type = object : TypeToken<List<ReportModel>?>() {}.type
                    val outputData : List<ReportModel> = gson.fromJson(gson.toJson(dataReport),listData)
                    report_server.value = outputData

                } else {
                    val gson = Gson()
                    val listData : Type = object : TypeToken<List<ReportModel>?>() {}.type
                    val outputData : List<ReportModel> = gson.fromJson(gson.toJson(dataReport),listData)
                    report_server.value = outputData
                    println("DATA ${res?.data}")
                }

            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                println(t.localizedMessage)
                println("gagal connect")
                Toast.makeText(
                    context,
                    "Kesalahan Server !",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}