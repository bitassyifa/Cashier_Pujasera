package com.projectassyifa.cashier_pujasera.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Connect {
    companion object {
        val BASE_URL_MEMBER=  "http://202.62.9.140//payment/"
        val BASE_URL_LOGIN =  "http://202.62.9.138:1234/"

        fun urlMember(): Retrofit {
            val koneksi = Retrofit
                .Builder()
                .baseUrl(BASE_URL_MEMBER)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return  koneksi
        }
        fun urlLogin(): Retrofit {
            val koneksi = Retrofit
                .Builder()
                .baseUrl(BASE_URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return  koneksi
        }


    }
}