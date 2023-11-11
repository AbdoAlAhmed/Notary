package com.theideal.notary.main.supplier.theSupplier

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.BillContact
import com.theideal.notary.databinding.ItemBillInfoBinding
import com.theideal.notary.utils.SwipeAdapter

class TheSupplierAdapter(
    private val theSupplierViewModel: TheSupplierViewModel,
    private val onClick: OnClick
) :
    ListAdapter<BillContact, TheSupplierAdapter.TheSupplierViewHolder>(TheSupplierDiffCall),
    SwipeAdapter {


    object TheSupplierDiffCall : DiffUtil.ItemCallback<BillContact>() {
        override fun areItemsTheSame(oldItem: BillContact, newItem: BillContact): Boolean {
            return oldItem.billId == newItem.billId
        }

        override fun areContentsTheSame(oldItem: BillContact, newItem: BillContact): Boolean {
            return oldItem == newItem
        }
    }

    class TheSupplierViewHolder(val binding: ItemBillInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(billContact: BillContact) {
            binding.billContact = billContact
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheSupplierViewHolder {
        return TheSupplierViewHolder(
            ItemBillInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TheSupplierViewHolder, position: Int) {
        val billContact = getItem(position)
        holder.bind(billContact)
        holder.itemView.setOnClickListener {
            onClick.onClick(billContact)
        }
    }

    class OnClick(val clickListener: (billContact: BillContact) -> Unit) {
        fun onClick(billContact: BillContact) = clickListener(billContact)
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
            theSupplierViewModel.dialogDeleteBill(item)
            submitList(list)
        }

    }

    private fun editItem(position: Int) {
        if (position in 0 until itemCount) {
            val item = getItem(position)
            val list = currentList.toMutableList()
            theSupplierViewModel.navToTheBillSupplier(item = item)
            submitList(list)
        }
    }

    fun removeItem(position: String) {
        val updatedList = currentList.toMutableList()
        updatedList.removeIf() { it.billId == position }
        submitList(updatedList)
    }


}