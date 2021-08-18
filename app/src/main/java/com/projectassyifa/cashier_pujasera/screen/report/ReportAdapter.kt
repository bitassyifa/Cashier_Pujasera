package com.projectassyifa.cashier_pujasera.screen.report

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.room.SaleModel
import kotlinx.android.synthetic.main.adapter_report_lokal.view.*

class ReportAdapter (var listReport :ArrayList<SaleModel>,var activity: Activity) : RecyclerView.Adapter<ReportAdapter.SaleVH>() {
    class SaleVH (val view : View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleVH {
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
      return SaleVH(

          LayoutInflater.from(parent.context).inflate(R.layout.adapter_report_lokal,parent,false)
      )
    }

    override fun onBindViewHolder(holder: SaleVH, position: Int) {
       val sale = listReport[position]

        holder.view.id_penjualan.text = sale.id_penjualan.toString()
        holder.view.nama_pelanggan.text = sale.nama_pelanggan
        holder.view.total_belanja.text = sale.total_harga.toString()
        holder.view.status.text = sale.status_data

        if (sale.status_data == "CLOSE"){
            holder.view.wadah_data.setBackgroundColor(Color.GREEN)

        }
        holder.view.wadah_data.setOnClickListener {


            val dialog = DialogClosingReport()
            val bundle= Bundle()

            dialog.arguments = bundle
            bundle.putString("id_pelanggan",sale.no_kartu)
            bundle.putString("nama_pelanggan",sale.nama_pelanggan)
            bundle.putString("id_sale", sale.id_penjualan.toString())
            bundle.putString("server",sale.server)
            bundle.putString("db",sale.db)
            bundle.putString("jumlah",sale.total_harga.toString())
            bundle.putString("tanggal",sale.tanggal)
            bundle.putString("created_by",sale.created_by)
            bundle.putString("status",sale.status_data)
            val ft = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            dialog.show(ft, ContentValues.TAG)

        }
//        holder.view.id_penjualan.setOnClickListener {
//            println("Klik data")
//            println("JUMLAH ${sale.total_harga}")
//        }

    }

    override fun getItemCount(): Int {
        return listReport.size
    }

    fun setData(list:List<SaleModel>){
        listReport.clear()
        listReport.addAll(list)
        notifyDataSetChanged()
    }
}