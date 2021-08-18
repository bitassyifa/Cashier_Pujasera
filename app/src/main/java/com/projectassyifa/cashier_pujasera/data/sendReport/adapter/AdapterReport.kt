package com.projectassyifa.cashier_pujasera.data.sendReport.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.data.sendReport.model.ReportModel

class AdapterReport (val listReportServer : List<ReportModel>,var activity : Activity)
    :RecyclerView.Adapter<ReportVH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_report_server,parent,false)
        return  ReportVH(view)
    }

    override fun onBindViewHolder(holder: ReportVH, position: Int) {

            holder.nama.text = listReportServer[position].nama_pelanggan
            holder.jumlah.text = listReportServer[position].jumlah
            holder.metode.text = listReportServer[position].metode_bayar
            holder.tanggal.text = listReportServer[position].tanggal


    }

    override fun getItemCount(): Int {
        return listReportServer.size
    }

}

class ReportVH (view: View) : RecyclerView.ViewHolder(view){

    var nama = view.findViewById<TextView>(R.id.nama_pelanggan1)
    var metode = view.findViewById<TextView>(R.id.metode1)
    var jumlah = view.findViewById<TextView>(R.id.total_belanja1)
    var tanggal = view.findViewById<TextView>(R.id.tgl1)
}
