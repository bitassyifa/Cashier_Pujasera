package com.projectassyifa.cashier_pujasera.data.login.viewmodel

import android.content.Context
import com.projectassyifa.cashier_pujasera.data.login.model.UserLoginModel
import com.projectassyifa.cashier_pujasera.data.login.repository.UserLoginRepo


import javax.inject.Inject

class UserLoginVM  @Inject constructor(var userLoginRepo: UserLoginRepo){
    var userLogin = userLoginRepo.userLogin
    var userLoginResponse = userLoginRepo.userLoginResponse

    fun loginUser(userLoginModel: UserLoginModel, context: Context){
        userLoginRepo.loginUser(userLoginModel,context)

    }
}