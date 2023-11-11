package com.theideal.notary.main.supplier.theSupplier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.Contact
import com.theideal.notary.databinding.ItemContactInfoBinding
import com.theideal.notary.utils.SwipeAdapter

class SupplierAdapter(
    private val supplierAndTheSupplierViewModel: SupplierAndTheSupplierViewModel,
    private val onClick: OnClick
) :
    ListAdapter<Contact, SupplierAdapter.SupplierViewHolder>(SupplierDiffUtil), SwipeAdapter {

    object SupplierDiffUtil : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.contactId == newItem.contactId
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

    inner class SupplierViewHolder(private val binding: ItemContactInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.contact = contact
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SupplierAdapter.SupplierViewHolder {
        return SupplierViewHolder(
            ItemContactInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SupplierAdapter.SupplierViewHolder, position: Int) {
        val contact = getItem(position)
        holder.itemView.setOnClickListener {
            onClick.onClick(contact)
        }
        holder.bind(contact)
    }

    class OnClick(val clickListener: (Contact) -> Unit) {
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
            supplierAndTheSupplierViewModel.deleteSupplierDialog(item)
            submitList(list)
        }

    }

    private fun editItem(position: Int) {
        if (position in 0 until itemCount) {
            val item = getItem(position)
            val list = currentList.toMutableList()
            supplierAndTheSupplierViewModel.editSupplierDialog(item)
            submitList(list)
        }
    }

}