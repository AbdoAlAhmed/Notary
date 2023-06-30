package com.theideal.notary.main.supplier.theSupplier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.ItemInfo
import com.theideal.notary.databinding.ItemBillInfoSupplierBinding

class TheSupplierBillInfoAdapter :
    ListAdapter<ItemInfo, TheSupplierBillInfoAdapter.TheSupplierBillInfoViewHolder>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<ItemInfo>() {
        override fun areItemsTheSame(oldItem: ItemInfo, newItem: ItemInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemInfo, newItem: ItemInfo): Boolean {
            return oldItem == newItem
        }
    }


    class TheSupplierBillInfoViewHolder(private val binding: ItemBillInfoSupplierBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemInfo: ItemInfo) {
            binding.itemInfo = itemInfo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheSupplierBillInfoViewHolder {
        return TheSupplierBillInfoViewHolder(
            ItemBillInfoSupplierBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TheSupplierBillInfoViewHolder, position: Int) {
        val itemInfo = getItem(position)
        holder.bind(itemInfo)
    }


}