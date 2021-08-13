package com.projectassyifa.cashier_pujasera.screen.report

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.container.MyApplication
import com.projectassyifa.cashier_pujasera.data.sendReport.model.SendReportModel
import com.projectassyifa.cashier_pujasera.data.sendReport.vm.SendReportVM
import com.projectassyifa.cashier_pujasera.room.SaleDB
import com.projectassyifa.cashier_pujasera.room.SaleModel
import com.projectassyifa.cashier_pujasera.screen.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_dialog_closing_report.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DialogClosingReport : DialogFragment() {

    val database by lazy { SaleDB(this.requireContext()) }

    @Inject
    lateinit var sendReportVM: SendReportVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.getWindow()?.setLayout(600, 400)
        val rootView : View = inflater.inflate(R.layout.fragment_dialog_closing_report, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nama_pelanggan = arguments?.getString("nama_pelanggan")
        val id_pelanggan = arguments?.getString("id_pelanggan")
        val id_sale= arguments?.getString("id_sale")
        val server = arguments?.getString("server")
        val db = arguments?.getString("db")
        val jumlah = arguments?.getString("jumlah")
        val tanggal = arguments?.getString("tanggal")
        val  created= arguments?.getString("created_by")
        val  status= arguments?.getString("status")

        nama_member.text = nama_pelanggan
        no_kartu.text = id_pelanggan
        jml.text = jumlah
        tgl.text = tanggal
        kasir.text = created
        stts.text =status
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val currentDate = sdf.format(Date())

        closing.isVisible = status != "CLOSE"

        closing.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                database.saleDao().updateSale(
                    SaleModel(id_sale.toString().toInt(),nama_pelanggan.toString(),id_pelanggan.toString(),jumlah.toString().toInt(),
                        currentDate.toString(),db.toString(),"CAGAK",created.toString(),"CLOSE")
                )

            }
//
            val dataSend = SendReportModel(
                server = server.toString(),
                db = db.toString(),
                jumlah = jumlah.toString(),
                id_pelanggan = id_pelanggan.toString(),
                nama_pelanggan = nama_pelanggan.toString(),
                created_by = created.toString()
            )
//            println("DATA JUMLAH ${dataSend.jumlah},DATA SERVER ${dataSend.server},DATA db ${dataSend.db},DATA id plnggn ${dataSend.id_pelanggan},DATA namapelanggan ${dataSend.nama_pelanggan},DATA created${dataSend.created_by}")
            sendReportVM.reporting(dataSend,requireContext())
         startActivity(Intent(this.context,HomeActivity::class.java))
        }
    }

}

