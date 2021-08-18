package com.projectassyifa.cashier_pujasera.data.income.api


import com.projectassyifa.cashier_pujasera.data.income.model.IncomeModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IncomeAPI {
    @POST("api_report_total")
    fun income(@Body incomeModel : IncomeModel): Call<IncomeModel>
}