package com.theideal.notary.main.supplier.theSupplier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.Item
import com.theideal.notary.databinding.ItemClientBillBinding

class TheSupplierBillAdapter :
    ListAdapter<Item, TheSupplierBillAdapter.TheSuppilerBillViewHolder>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }

    class TheSuppilerBillViewHolder(private val binding: ItemClientBillBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheSupplierBillAdapter.TheSuppilerBillViewHolder {
        return TheSuppilerBillViewHolder(
            ItemClientBillBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TheSupplierBillAdapter.TheSuppilerBillViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}