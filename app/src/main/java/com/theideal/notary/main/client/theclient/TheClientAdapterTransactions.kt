package com.theideal.notary.main.client.theclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.theideal.data.model.BillContact
import com.theideal.notary.databinding.ItemBillInfoBinding
import com.theideal.notary.utils.SwipeAdapter

class TheClientAdapterTransactions(
    private val theClientViewModel: TheClientViewModel,
    private val onClick: OnClick
) :
    ListAdapter<BillContact, TheClientAdapterTransactions.TheClientViewHolder>(
        DiffCallBack
    ), SwipeAdapter {
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
        sortList()
    }

    class OnClick(val clickListener: (billContact: BillContact) -> Unit) {
        fun onClick(billContact: BillContact) = clickListener(billContact)
    }

    private fun sortList() {
        val sortedList = currentList.sortedWith(compareBy({ item ->
            when (item.status) {
                "open" -> 0
                "deferred" -> 1
                "closed" -> 2
                else -> 3
            }
        }, { it.createAt }))
        submitList(sortedList)
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
            theClientViewModel.deleteBillConfirmDialog(item)
            submitList(list)
        }
    }

    private fun editItem(position: Int) {
        if (position in 0 until itemCount) {
            val item = getItem(position)
            val list = currentList.toMutableList()
            theClientViewModel.navToClientBillWithBillContact(item)
            submitList(list)
        }
    }

    fun removeItemAt(billContact: BillContact) {
        val list = currentList.toMutableList()
        list.removeIf { it.billId == billContact.billId }
        submitList(list)

    }


}
