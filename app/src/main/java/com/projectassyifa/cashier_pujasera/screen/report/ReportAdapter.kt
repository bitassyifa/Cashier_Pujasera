package com.projectassyifa.cashier_pujasera.screen.report

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.room.SaleModel
import kotlinx.android.synthetic.main.adapter_report.view.*

class ReportAdapter (private val listReport :ArrayList<SaleModel>,var activity: Activity) : RecyclerView.Adapter<ReportAdapter.SaleVH>() {
    class SaleVH (val view : View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleVH {
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
      return SaleVH(

          LayoutInflater.from(parent.context).inflate(R.layout.adapter_report,parent,false)
      )
    }

    override fun onBindViewHolder(holder: SaleVH, position: Int) {
       val sale = listReport[position]

        holder.view.id_penjualan.text = sale.id_penjualan.toString()
        holder.view.nama_pelanggan.text = sale.nama_pelanggan
        holder.view.total_belanja.text = sale.total_harga.toString()
        holder.view.status.text = sale.status_data
        holder.view.wadah_data.setOnClickListener {
            println("Nama ${sale.nama_pelanggan}")
            println("Server ${sale.server}")
            println("DB ${sale.db}")
            println("JUMLAH ${sale.total_harga}")
            println("ID pelanggan ${sale.no_kartu}")
            println("stts${sale.status_data}")

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