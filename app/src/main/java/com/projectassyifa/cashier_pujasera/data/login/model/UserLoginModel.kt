package com.projectassyifa.cashier_pujasera.data.login.model

class UserLoginModel (
    var email : String ="default",
    var username: String = "default",
    var password: String = "default",
    var db : String ="",
    var server : String =""
){}

class UserLoginResponseModel (
    var id_pegawai : String = " ",
    var email : String ="",
    var nama_pegawai : String = " ",
    var foto : String = " ",
    var db : String ="",
    var server : String =""


){}