package com.projectassyifa.cashier_pujasera.data.merchant.api

import com.projectassyifa.cashier_pujasera.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET

interface MerchantAPI {
    @GET("api_toko")
    fun merchant():Call<ResponseAPI>
}