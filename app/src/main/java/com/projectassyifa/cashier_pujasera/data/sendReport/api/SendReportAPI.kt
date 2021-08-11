package com.projectassyifa.cashier_pujasera.data.sendReport.api

import com.projectassyifa.cashier_pujasera.data.sendReport.model.SendReportModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SendReportAPI {
    @POST("api_penjualan")
    fun reporting(@Body sendReportModel : SendReportModel):Call<SendReportModel>
}
