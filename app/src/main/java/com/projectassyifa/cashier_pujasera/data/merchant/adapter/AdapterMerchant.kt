package com.projectassyifa.cashier_pujasera.data.merchant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.projectassyifa.cashier_pujasera.R
import com.projectassyifa.cashier_pujasera.data.merchant.model.MerchantModel

class AdapterMerchant (var dataSource : List<MerchantModel>, val context: Context) : BaseAdapter() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var kode_db : String? = null

    override fun getCount(): Int {
        return  dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View
        val vh : ItemHolder
        if (convertView == null){
            view = inflater.inflate(R.layout.adapter_merchant,parent,false)
            vh = ItemHolder(view)
            view?.tag =vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.nama_merchant.text = dataSource.get(position).nama_merchant
        kode_db = dataSource.get(position).db
        return view
    }

    class ItemHolder (row : View?){
        val nama_merchant : TextView
        init {
            nama_merchant = row?.findViewById(R.id.nama_merchants) as TextView
        }
    }

}