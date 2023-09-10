package com.theideal.notary.main.client.theclient

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.theideal.data.model.Contact

class AutoCompleteAdapter(context: Context, val list: List<Contact>) :
    ArrayAdapter<Contact>(context, android.R.layout.simple_spinner_dropdown_item, list) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Contact? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val contact = list[position]
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = contact.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val contact = list[position]
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = contact.name
        return view
    }

}