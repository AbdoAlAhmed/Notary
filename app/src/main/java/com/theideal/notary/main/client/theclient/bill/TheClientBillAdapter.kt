package com.theideal.notary.main.client.theclient.bill

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.Item
import com.theideal.notary.databinding.ItemClientBillBinding
import com.theideal.notary.utils.SwipeAdapter

class TheClientBillAdapter(
    private val theClientBillViewModel: ClientBillViewModel,
    private val onClick: OnClick,
) :
    ListAdapter<Item, TheClientBillAdapter.TheClientBillViewHolder>(DiffCallBack),
    SwipeAdapter {
    object DiffCallBack : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    class TheClientBillViewHolder(private val binding: ItemClientBillBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.executePendingBindings()
        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheClientBillViewHolder {
        return TheClientBillViewHolder(
            ItemClientBillBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TheClientBillViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick.onClick(item)
        }
    }



    class OnClick(val clickListener: (item: Item) -> Unit) {
        fun onClick(item: Item) = clickListener(item)

    }

    override fun onItemDelete(position: Int) {
        deleteItem(position)
    }

    override fun onItemEdit(position: Int) {
        editItem(position)
    }

    private fun editItem(position: Int) {
        if (position in 0 until itemCount) {
            val editedItem = getItem(position)
            val updatedList = currentList.toMutableList()
            submitList(updatedList)
            theClientBillViewModel.updateDialogOpen(item = editedItem)

        }
    }

    private fun deleteItem(position: Int) {
        if (position in 0 until itemCount) {
            val deletedItem = getItem(position)
            val updatedList = currentList.toMutableList()
            theClientBillViewModel.confirmDeleteDialogOpen(deletedItem.itemId)
            submitList(updatedList)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setDataChanged() {
        notifyDataSetChanged()
    }

    fun addItem(item: Item) {
        val updatedList = currentList.toMutableList()
        updatedList.add(item)
        submitList(updatedList)
    }

    fun removeItem(item: String) {
        val updatedList = currentList.toMutableList()
        updatedList.removeIf { it.itemId == item }
        submitList(updatedList)
    }

    fun updateItem(item: Item) {
        val updatedList = currentList.toMutableList()
        updatedList.replaceAll { if (it.itemId == item.itemId) item else it }
        submitList(updatedList)
        notifyItemChanged(currentList.indexOf(item))
    }


    fun isListEmpty(): Boolean {
        return currentList.toMutableList().isEmpty()
    }

    fun refreshList() {
        submitList(currentList.toMutableList())
    }


}