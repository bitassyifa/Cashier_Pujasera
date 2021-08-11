package com.projectassyifa.cashier_pujasera.room

import androidx.room.*

@Dao
interface SaleDao {

    @Insert
    suspend fun addSale(saleModel: SaleModel)

    @Update
    suspend fun updateSale(saleModel: SaleModel)

    @Delete
    suspend fun deleteSale(saleModel: SaleModel)

    @Query("SELECT * FROM salemodel")
    suspend fun getSale():List<SaleModel>
}