package com.theideal.notary.main.client.saletransactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.Contact
import com.theideal.notary.databinding.ItemContactInfoBinding

class SaleTransactionsAdapter(private val clickListener: SaleTransactionsListener) :
    ListAdapter<Contact, SaleTransactionsAdapter.SaleTransactionsViewHolder>(
        SaleTransactionsDiffCallback
    ) {

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
}