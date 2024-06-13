package com.example.a6112_final_project_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a6112_final_project_kotlin.databinding.ItemRowBinding

class ItemAdapter(
    private var items: List<Item>,
    private val itemClickListener: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item, itemClickListener: (Item) -> Unit) {
            binding.apply {
                textViewName.text = item.name
                textViewDescription.text = item.description
                textViewQty.text = item.currQuantity.toString()
                editItemBtn.setOnClickListener { itemClickListener(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, itemClickListener)
    }
}
