package com.projectassyifa.cashier_pujasera.data.login.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.cashier_pujasera.data.login.api.UserLoginAPI
import com.projectassyifa.cashier_pujasera.data.login.model.UserLoginModel
import com.projectassyifa.cashier_pujasera.data.login.model.UserLoginResponseModel
import com.projectassyifa.cashier_pujasera.utils.ResponseAPI

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class UserLoginRepo @Inject constructor( val userLoginApi : UserLoginAPI) {
    var userLogin = MutableLiveData<ResponseAPI>()
    var userLoginResponse = MutableLiveData<UserLoginResponseModel>()



    fun loginUser(userLoginModel: UserLoginModel, context: Context){
        println("USER LOGIN ${userLoginModel.username},${userLoginModel.password}")
        userLoginApi.loginUser(userLoginModel).enqueue(object : Callback<ResponseAPI> {
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                if (response.code()==200){
                    val res = response.body()
                    val resData = res?.data
                    val gson = Gson()
                    val listOfMyClassObject: Type = object : TypeToken<List<UserLoginResponseModel>>() {}.type
                    val outputList: List<UserLoginResponseModel> = gson.fromJson(gson.toJson(resData), listOfMyClassObject)

                    userLogin.value = res
                    userLoginResponse.value = outputList[0]


                    Toast.makeText(context,"Login Success ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Login Failed", Toast.LENGTH_SHORT).show()
                    println("USER NOT FOUND")
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                print("MASUK ONFAILURE")
            }

        })
    }
}