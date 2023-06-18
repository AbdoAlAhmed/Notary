package com.theideal.notary.main.client.saletransactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.Contact
import com.theideal.notary.databinding.ItemContactInfoBinding
import com.theideal.notary.utils.SwipeAdapter

class SaleTransactionsAdapter(private val clickListener: SaleTransactionsListener) :
    ListAdapter<Contact, SaleTransactionsAdapter.SaleTransactionsViewHolder>(
        SaleTransactionsDiffCallback
    ), SwipeAdapter {

    object SaleTransactionsDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.contactId == newItem.contactId
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }

    class SaleTransactionsViewHolder(private val binding: ItemContactInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.contact = contact
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SaleTransactionsViewHolder {
        return SaleTransactionsViewHolder(
            ItemContactInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: SaleTransactionsViewHolder,
        position: Int,
    ) {
        val contact = getItem(position)
        holder.bind(contact)
        holder.itemView.setOnClickListener {
            clickListener.onClick(contact)
        }
    }

    class SaleTransactionsListener(val clickListener: (contact: Contact) -> Unit) {
        fun onClick(contact: Contact) = clickListener(contact)
    }


    fun getNameBySearchAndPutItInTheFirstPosition(name: String): String {
        var name = name
        for (i in 0 until itemCount) {
            if (getItem(i).name == name) {
                name = getItem(i).name
                currentList.first().name = name
            }
        }
        return name
    }

    override fun onItemDelete(position: Int) {
        deleteItem(position)
    }

    override fun onItemEdit(position: Int) {
        editItem(position)
    }

    override fun setDataChanged() {
        notifyDataSetChanged()
    }

    private fun deleteItem(position: Int) {


    }

    private fun editItem(position: Int) {

    }
}