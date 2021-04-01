package com.example.ass1mcc.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ass1mcc.modle.ContactUser
import com.example.ass1mcc.R

class AdapterCon(private val context: Context, var data: ArrayList<ContactUser>) : BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(i: Int): Any {
        return data[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView = inflater.inflate(R.layout.item_contact, parent, false)

        val name = itemView.findViewById(R.id.tvName) as TextView
        name.text = data[position].name

        val address = itemView.findViewById(R.id.tvAddress) as TextView
        address.text = data[position].address

        val number = itemView.findViewById(R.id.tvNumber) as TextView
        number.text = data[position].number


        return itemView
    }
}