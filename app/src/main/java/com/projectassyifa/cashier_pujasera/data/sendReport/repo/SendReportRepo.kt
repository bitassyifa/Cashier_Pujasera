package com.projectassyifa.cashier_pujasera.data.sendReport.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.cashier_pujasera.data.member.model.MemberModel
import com.projectassyifa.cashier_pujasera.data.sendReport.api.SendReportAPI
import com.projectassyifa.cashier_pujasera.data.sendReport.model.SendReportModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class SendReportRepo @Inject constructor(val sendReportAPI: SendReportAPI)  {
    var reportResponse = MutableLiveData<SendReportModel>()


    fun reporting(sendReportModel :SendReportModel , context: Context){
        sendReportAPI.reporting(sendReportModel).enqueue(object : Callback<SendReportModel>{
            override fun onResponse(
                call: Call<SendReportModel>,
                response: Response<SendReportModel>
            ) {
                val res = response.body()
                val resData = res
                if (resData != null) {
                    val gson = Gson()
                    val list: Type = object : TypeToken<SendReportModel>() {}.type
                    val Output: SendReportModel =
                        gson.fromJson(gson.toJson(res), list)
                    reportResponse.value = Output
//                    memberResponse.value?.kode =response.code()

                    Toast.makeText(
                        context,
                        "data berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "gagal menyimpan data",
                        Toast.LENGTH_SHORT
                    ).show()


                }
            }

            override fun onFailure(call: Call<SendReportModel>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    context,
                    "Kesalahan server!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}