package com.projectassyifa.cashier_pujasera.data.sendReport.api

import com.projectassyifa.cashier_pujasera.data.sendReport.model.ReportModel
import com.projectassyifa.cashier_pujasera.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportAPI {
    @POST("api_report")
    fun report(@Body reportModel : ReportModel): Call<ResponseAPI>
}