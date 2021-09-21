package com.projectassyifa.cashier_pujasera.data.login.api


import com.projectassyifa.cashier_pujasera.data.login.model.UserLoginModel
import com.projectassyifa.cashier_pujasera.utils.ResponseAPI

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserLoginAPI {
    @POST("api_login")
    fun loginUser(@Body userLoginModel : UserLoginModel):Call<ResponseAPI>
}