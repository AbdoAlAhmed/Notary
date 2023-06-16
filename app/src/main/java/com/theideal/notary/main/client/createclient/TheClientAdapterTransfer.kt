package com.theideal.notary.main.client.createclient

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.theideal.data.model.Transfer
import com.theideal.notary.databinding.ItemTransferBinding
import com.theideal.notary.utils.SwipeAdapter

class TheClientAdapterTransfer(private val onClick: OnClick) :
    androidx.recyclerview.widget.ListAdapter<Transfer, TheClientAdapterTransfer.ViewHolder>(
        DiffCallBAck
    ),
    SwipeAdapter {

    object DiffCallBAck : DiffUtil.ItemCallback<Transfer>() {
        override fun areItemsTheSame(oldItem: Transfer, newItem: Transfer): Boolean {
            return oldItem.transferId == newItem.transferId
        }

        override fun areContentsTheSame(oldItem: Transfer, newItem: Transfer): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: ItemTransferBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(transfer: Transfer) {
            binding.transfer = transfer
            binding.executePendingBindings()
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemTransferBinding.inflate(
                android.view.LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transfer = getItem(position)
        holder.itemView.setOnClickListener {
            onClick.onClick(transfer)
        }
        holder.bind(transfer)
    }

    class OnClick(val clickListener: (transfer: Transfer) -> Unit) {
        fun onClick(transfer: Transfer) = clickListener(transfer)
    }


    override fun onItemDelete(position: Int) {
    }

    override fun onItemEdit(position: Int) {
    }


    override fun setDataChanged() {
    }

}