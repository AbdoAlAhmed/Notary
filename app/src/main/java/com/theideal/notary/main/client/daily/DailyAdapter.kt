package com.theideal.notary.main.client.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.notary.databinding.ItemContactInfoDailyBinding
import com.theideal.notary.main.client.DailyAndClientsViewModel
import com.theideal.notary.utils.SwipeAdapter

class DailyAdapter(
    private val dailyAndClientsViewModel: DailyAndClientsViewModel,
    private val clickListener: SaleTransactionsListener
) : ListAdapter<Pair<Contact, BillContact>, DailyAdapter.SaleTransactionsViewHolder>(
    SaleTransactionsDiffCallback
), SwipeAdapter {

    object SaleTransactionsDiffCallback : DiffUtil.ItemCallback<Pair<Contact, BillContact>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Contact, BillContact>,
            newItem: Pair<Contact, BillContact>
        ): Boolean {
            return oldItem.first.contactId == newItem.first.contactId
        }

        override fun areContentsTheSame(
            oldItem: Pair<Contact, BillContact>,
            newItem: Pair<Contact, BillContact>
        ): Boolean {
            return oldItem == newItem
        }


    }

    class SaleTransactionsViewHolder(private val binding: ItemContactInfoDailyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Pair<Contact, BillContact>) {
            binding.contact = contact.first
            binding.billContact = contact.second
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SaleTransactionsViewHolder {
        return SaleTransactionsViewHolder(
            ItemContactInfoDailyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
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
        fun onClick(contact: Pair<Contact, BillContact>) = clickListener(contact.first)
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
        if (position in 0 until itemCount) {
            val item = getItem(position)
            val list = currentList.toMutableList()
            dailyAndClientsViewModel.deleteDialogOpen(item.first)
            submitList(list)
        }

    }

    private fun editItem(position: Int) {
        if (position in 0 until itemCount) {
            val item = getItem(position)
            val list = currentList.toMutableList()
            dailyAndClientsViewModel.editDialogOpen(item.first)
            submitList(list)
        }
    }


}