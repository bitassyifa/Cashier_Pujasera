package com.projectassyifa.cashier_pujasera.data.member.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectassyifa.cashier_pujasera.data.member.api.CekPinAPI
import com.projectassyifa.cashier_pujasera.data.member.api.MemberAPI
import com.projectassyifa.cashier_pujasera.data.member.model.MemberModel
import com.projectassyifa.cashier_pujasera.utils.ResponseAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class CekPinRepo @Inject constructor( val memberAPI: CekPinAPI) {

    var userLogin = MutableLiveData<MemberModel>()
    var memberResponse = MutableLiveData<MemberModel>()

    fun member(memberModel: MemberModel, context: Context){
        memberAPI.member(memberModel).enqueue(object : Callback<MemberModel>{
            override fun onResponse(call: Call<MemberModel>, response: Response<MemberModel>) {
                val res = response.body()
                val resData = res
                if (resData?.status != false) {
                    val gson = Gson()
                    val listMember: Type = object : TypeToken<MemberModel>() {}.type
                    val Output: MemberModel =
                        gson.fromJson(gson.toJson(res), listMember)
                    memberResponse.value = Output
//                    memberResponse.value?.kode =response.code()
                    println("RES ${res?.status}")
                    Toast.makeText(
                        context,
                        "Succes",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Pin tidak cocok, cobalagi",
                        Toast.LENGTH_SHORT
                    ).show()
                    println("RES ${res?.status}")
                    userLogin.value = res

                }

            }

            override fun onFailure(call: Call<MemberModel>, t: Throwable) {
                t.printStackTrace()
                print("DATA MEMBER FAILED ")
            }

        })
    }
}