package com.theideal.notary.main.client.createclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.theideal.data.model.BillContact
import com.theideal.notary.databinding.ItemBillInfoBinding

class TheClientAdapterTransactions(private val onClick: OnClick) :
   ListAdapter<BillContact, TheClientAdapterTransactions.TheClientViewHolder>(
        DiffCallBack
    ) {
    object DiffCallBack : DiffUtil.ItemCallback<BillContact>() {
        override fun areItemsTheSame(oldItem: BillContact, newItem: BillContact): Boolean {
            return oldItem.billId == newItem.billId
        }

        override fun areContentsTheSame(oldItem: BillContact, newItem: BillContact): Boolean {
            return oldItem == newItem
        }
    }

    class TheClientViewHolder(private val binding: ItemBillInfoBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(billContact: BillContact) {
            binding.billContact = billContact
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheClientViewHolder {
        return TheClientViewHolder(
            ItemBillInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TheClientViewHolder, position: Int) {
        val billContact = getItem(position)
        holder.itemView.setOnClickListener {
            onClick.onClick(billContact)
        }
        holder.bind(billContact)
    }

    class OnClick(val clickListener: (billContact: BillContact) -> Unit) {
        fun onClick(billContact: BillContact) = clickListener(billContact)
    }
}