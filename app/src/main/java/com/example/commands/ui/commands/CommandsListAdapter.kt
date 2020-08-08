package com.example.commands.ui.commands

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.commands.R
import com.example.commands.models.Command
import kotlinx.android.synthetic.main.item_command.view.*

class CommandsListAdapter : ListAdapter<Command, CommandsListAdapter.ItemViewHolder>(
    DiffCallback()
)  {

    var onItemClick: ((Command, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_command,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun removeCommand(position: Int) {
        val newList = currentList.toList() as ArrayList
        newList.removeAt(position)
        submitList(newList)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Command) = with(itemView) {
            itemView.how_to.text = item.howTo
            itemView.line.text = item.line

            itemView.delete_ic.setOnClickListener {
                onItemClick?.invoke(item, adapterPosition)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Command>() {
    override fun areItemsTheSame(oldItem: Command, newItem: Command): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Command, newItem: Command): Boolean {
        return (oldItem.howTo.equals(newItem.howTo) || oldItem.line.equals(newItem.line))
    }
}