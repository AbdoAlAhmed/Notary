package com.theideal.notary.main.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theideal.data.model.MoreItem
import com.theideal.notary.databinding.ItemMoreBinding
import com.theideal.notary.utils.SwipeAdapter


class MoreAdapter(private val OnClick:OnclickListener) : ListAdapter<MoreItem, MoreAdapter.MoreViewHolder>(MoreDiffCall), SwipeAdapter {

    object MoreDiffCall : DiffUtil.ItemCallback<MoreItem>() {
        override fun areItemsTheSame(oldItem: MoreItem, newItem: MoreItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoreItem, newItem: MoreItem): Boolean {
            return oldItem == newItem
        }

    }

    class MoreViewHolder(private val binding: ItemMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(moreItem: MoreItem) {
            binding.moreItem = moreItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreViewHolder {
        return MoreViewHolder(
            ItemMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoreViewHolder, position: Int) {
        val moreItem = getItem(position)
        holder.bind(moreItem)
        holder.itemView.setOnClickListener {
            OnClick.onClick(moreItem)
        }
    }

    class OnclickListener(val clickListener: (moreItem: MoreItem) -> Unit) {
        fun onClick(moreItem: MoreItem) = clickListener(moreItem)
    }

    override fun onItemDelete(position: Int) {

    }

    override fun onItemEdit(position: Int) {
    }

    override fun setDataChanged() {
    }

}