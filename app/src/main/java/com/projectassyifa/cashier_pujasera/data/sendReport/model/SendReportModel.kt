package com.projectassyifa.cashier_pujasera.data.sendReport.model

class SendReportModel(
    var server: String ="",
    var db: String ="",
    var jumlah: String ="",
    var id_pelanggan: String ="",
    var nama_pelanggan: String ="",
    var created_by: String ="",
    var success: Boolean =false,
    var kirim_fadi: Boolean = false
){}
