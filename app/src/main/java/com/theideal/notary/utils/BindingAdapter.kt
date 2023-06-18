package com.theideal.notary.utils

import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.Contact
import com.theideal.notary.R
import java.text.SimpleDateFormat
import java.util.Locale


@BindingAdapter("progressBarVisibility")
fun bindProgressBar(progressBar: ProgressBar, visibility: Boolean) {
    progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_data")
fun bindDataToRecyclerView(recyclerView: RecyclerView, data: List<Any>?) {
    val adapter = recyclerView.adapter as androidx.recyclerview.widget.ListAdapter<*, *>?
    adapter!!.submitList(data as List<Nothing>?)
}


@BindingAdapter("bind_auto_complete_text", "bind_contact")
fun bindAutoComplete(
    autoCompleteText: AutoCompleteTextView,
    list: List<Contact>?,
    contact: Contact
) {
    if (list != null) {
        val adapter = ArrayAdapter(
            autoCompleteText.context,
            android.R.layout.simple_spinner_dropdown_item,
            list.map { it.name }
        )
        autoCompleteText.setAdapter(adapter)
        autoCompleteText.setOnItemClickListener { parent, _, position, _ ->
            val chosenName = parent.getItemAtPosition(position).toString()
            val chosenContact = list.find { it.name == chosenName }
            chosenContact?.let {
                contact.contactId = it.contactId
                contact.name = it.name
                autoCompleteText.setText(it.name, false)
            }
        }
        autoCompleteText.setText(contact.name, false)
        adapter.notifyDataSetChanged()
    }
}


@BindingAdapter("set_double_text_to_empty")
fun bindDoubleTextToEmpty(textView: TextView, string: String) {
    textView.text = if (string == "0.0") "" else string
}


@BindingAdapter("status_text_color")
fun TextView.setStatusTextColor(status: String) {
    when (status) {
        "open" -> setTextColor(resources.getColor(R.color.dark_ocean_blue, null))
        "closed" -> setTextColor(resources.getColor(R.color.black, null))
        "deferred" -> setTextColor(resources.getColor(R.color.ride_red, null))
    }
}

@BindingAdapter("timestamp_to_date")
fun TextView.setTimestampToDate(timestamp: com.google.firebase.Timestamp) {
    val dateFormat = SimpleDateFormat("yy-MM-dd  HH:mm", Locale.getDefault())
    text = dateFormat.format(timestamp.toDate())
}

@BindingAdapter("set_empty_card_visibility")
fun bindEmptyCardVisibility(card: CardView, string: String) {
    card.visibility = if (string == "0.0") View.GONE else View.VISIBLE
}

@BindingAdapter("card_remaining_visibility_amount", "card_remaining_visibility_paid")
fun CardView.setRemainingMoneyVisibility(amount: String, paid: String) {
    visibility =
        if (amount > "0.0" && paid > "0.0") View.VISIBLE else View.GONE
}


@BindingAdapter("set_text_color")
fun TextView.setTextColor(status: String) {
    when (status) {
        "DEPOSIT" -> setTextColor(resources.getColor(R.color.dark_ocean_blue, null))
        "WITHDRAW" -> setTextColor(resources.getColor(R.color.black, null))
    }
}

@BindingAdapter("set_pay_discount_btn_text_visibility_enabled")
fun TextView.setPayVisibility(status: String) {
    isVisible = status != "closed"
}

@BindingAdapter("set_paid_visibility")
fun TextView.setPaidVisibility(status: String) {
    isVisible = status != "open"
}

@BindingAdapter("set_texted")
fun TextView.setTexted(string: String) {
    text = string.toString()
}

@BindingAdapter("set_paid_enabled")
fun TextView.setPaidEnabled(status: String) {
    isEnabled = status == "deferred"
}


