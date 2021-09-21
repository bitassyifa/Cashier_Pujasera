package com.projectassyifa.cashier_pujasera.data.merchant.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.cashier_pujasera.data.merchant.api.MerchantAPI
import com.projectassyifa.cashier_pujasera.data.merchant.model.MerchantModel
import com.projectassyifa.cashier_pujasera.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class MerchantRepo @Inject constructor(var merchantAPI: MerchantAPI){
    var data_merchant : MutableLiveData<List<MerchantModel>> = MutableLiveData()

    fun merchant(context: Context){
        merchantAPI.merchant().enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val responseBody = response.body()
                val data = responseBody?.data
                if (data ==  null){
                    Toast.makeText(
                        context,
                        "Merchant NULL",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    println("DATA MERCHANT REPO $data")
                    val gson = Gson()
                    val listData : Type = object  : TypeToken<List<MerchantModel>?>() {}.type
                    val responseData : List<MerchantModel> = gson.fromJson(gson.toJson(data),listData)
                    data_merchant.value = responseData
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Periksa Koneksi Jaringan",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

}