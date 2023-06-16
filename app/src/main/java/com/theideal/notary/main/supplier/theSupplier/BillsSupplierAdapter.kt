package com.theideal.notary.main.supplier.theSupplier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.BillContact
import com.theideal.notary.databinding.ItemBillInfoBinding

class BillsSupplierAdapter(private val onClick: OnClick) :
    ListAdapter<BillContact, BillsSupplierAdapter.BillSupplierViewHolder>(DiffCallBack) {
    class BillSupplierViewHolder(val binding: ItemBillInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(billContact: BillContact) {
            binding.billContact = billContact
            binding.executePendingBindings()
        }
    }

    object DiffCallBack : DiffUtil.ItemCallback<BillContact>() {
        override fun areItemsTheSame(oldItem: BillContact, newItem: BillContact): Boolean {
            return oldItem.billId == newItem.billId
        }

        override fun areContentsTheSame(oldItem: BillContact, newItem: BillContact): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BillSupplierViewHolder {
        return BillSupplierViewHolder(ItemBillInfoBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(
        holder: BillSupplierViewHolder,
        position: Int,
    ) {
        val billContact = getItem(position)
        holder.bind(billContact)
        holder.itemView.setOnClickListener {
            onClick.onClick(billContact)
        }

    }

    class OnClick(val clickListener: (billContact: BillContact) -> Unit) {
        fun onClick(billContact: BillContact) = clickListener(billContact)
    }
}