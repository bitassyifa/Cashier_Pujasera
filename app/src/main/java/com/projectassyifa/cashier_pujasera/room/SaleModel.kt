package com.projectassyifa.cashier_pujasera.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SaleModel (
    @PrimaryKey(autoGenerate = true)
    val id_penjualan : Int,
    val nama_pelanggan : String ,
    val no_kartu : String,
    val total_harga :Int,
    val tanggal : String,
    val db : String,
    val server : String,
    val created_by : String,
    val status_data : String

        )