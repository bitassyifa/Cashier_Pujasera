package com.projectassyifa.cashier_pujasera.data.login.model

class UserLoginModel (
    var email : String ="default",
    var username: String = "default",
    var password: String = "default"
){}

class UserLoginResponseModel (
    var id_pegawai : String = " ",
    var email : String ="",
    var nama_pegawai : String = " ",
    var foto : String = " ",
    var no_telp : String = " ",
    var bidang : String = " ",
    var unit : String = " ",
    var divisi : String = " ",
    var alamat_tinggal : String = " ",
    var tgl_lahir : String = " ",
    var agent : String = "",
    var status_pegawai : String = "",
    var status_aktif : String = "",
    var tanggal_mulai_tugas : String = "",
    var fungsional_01: String = "",
    var struktural : String = "",
    var atasan_langsung : String = "",
    var nip : String = " "
){}