package com.theideal.notary.main.client.createclient

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.theideal.data.model.Transfer
import com.theideal.notary.databinding.ItemTransferBinding
import com.theideal.notary.utils.SwipeAdapter

class TheClientAdapterTransfer(
    private val theClientViewModel: TheClientViewModel,
    private val onClick: OnClick
) :
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
        sortList()
    }

    class OnClick(val clickListener: (transfer: Transfer) -> Unit) {
        fun onClick(transfer: Transfer) = clickListener(transfer)
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

    private fun sortList() {
        val sortedList =
            currentList.sortedWith(compareBy({ it.typeOfFinancialTransfer }, { it.createAt }))
        submitList(sortedList)
    }

    fun addItem(transfer: Transfer) {
        val list = currentList.toMutableList()
        list.add(transfer)
        sortList()
        submitList(list)
    }

    private fun editItem(position: Int) {
        if (position in 0 until itemCount) {
            val item = getItem(position)
            val list = currentList.toMutableList()
            theClientViewModel.editDialog(item)
            submitList(list)

        }
    }

    private fun deleteItem(position: Int) {
        if (position in 0 until itemCount) {
            val item = getItem(position)
            val list = currentList.toMutableList()
            theClientViewModel.deleteDialog(item)
            submitList(list)
        }
    }

    fun updateItemAt(transfer: Transfer) {
        val list = currentList.toMutableList()
        list.replaceAll { if (it.transferId == transfer.transferId) transfer else it }
        submitList(list)
        notifyItemChanged(list.indexOf(transfer))
    }


    fun removeItemAt(transfer: Transfer) {
        val list = currentList.toMutableList()
        list.removeIf { it.transferId == transfer.transferId }
        submitList(list)
    }

    fun refreshList() {
        val updatedList = currentList.toMutableList()
        submitList(updatedList)
    }

}