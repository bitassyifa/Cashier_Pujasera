package com.projectassyifa.cashier_pujasera.data.member.api

import com.projectassyifa.cashier_pujasera.data.member.model.MemberModel
import com.projectassyifa.cashier_pujasera.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberAPI {
    @POST("api_saldo")
    fun member(@Body memberModel : MemberModel):Call<MemberModel>
}