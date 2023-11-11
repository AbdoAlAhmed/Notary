package com.theideal.notary.main.client.allclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.Contact
import com.theideal.notary.databinding.ItemContactInfoBinding
import com.theideal.notary.main.client.DailyAndClientsViewModel
import com.theideal.notary.main.client.daily.DailyAdapter
import com.theideal.notary.utils.SwipeAdapter

class ClientsAdapter(
    private val dailyAndClientsViewModel: DailyAndClientsViewModel,
    private val clickListener: ClickListener
) :
    ListAdapter<Contact, ClientsAdapter.ClientsViewHolder>(
        ClientsDiffCallback
    ), SwipeAdapter {

    object ClientsDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.contactId == newItem.contactId
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }


    class ClientsViewHolder(private val binding: ItemContactInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.contact = contact
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        return ClientsViewHolder(
            ItemContactInfoBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
        holder.itemView.setOnClickListener {
            clickListener.onClick(contact)
        }
    }

    class ClickListener(val clickListener: (contact: Contact) -> Unit) {
        fun onClick(contact: Contact) = clickListener(contact)
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
            dailyAndClientsViewModel.deleteDialogOpen(item)
            submitList(list)
        }

    }

    private fun editItem(position: Int) {
        if (position in 0 until itemCount) {
            val item = getItem(position)
            val list = currentList.toMutableList()
            dailyAndClientsViewModel.editDialogOpen(item)
            submitList(list)
        }
    }

}