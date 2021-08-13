package com.projectassyifa.cashier_pujasera.room

import androidx.lifecycle.LiveData
import androidx.room.*
import java.text.SimpleDateFormat
import java.util.*


@Dao
interface SaleDao {

    @Insert
    suspend fun addSale(saleModel: SaleModel)

    @Update
    suspend fun updateSale(saleModel: SaleModel)

    @Delete
    suspend fun deleteSale(saleModel: SaleModel)

    @Query("SELECT * FROM salemodel WHERE tanggal = :tgl ORDER BY id_penjualan DESC ")
    suspend fun getSale(tgl : String):List<SaleModel>


}