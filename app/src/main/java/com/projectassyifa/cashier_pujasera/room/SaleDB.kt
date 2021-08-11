package com.projectassyifa.cashier_pujasera.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [SaleModel::class],
    version = 1
)

abstract class SaleDB :RoomDatabase(){
    abstract fun saleDao() : SaleDao

    companion object {
        @Volatile private var instance : SaleDB? =null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: builDatabase(context).also {
                instance = it
            }
        }

        private fun builDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,SaleDB::class.java,
            "sale.db"
        ).build()
    }
}