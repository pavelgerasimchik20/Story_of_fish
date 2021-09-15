package com.geras.fishistory.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geras.fishistory.R
import com.geras.fishistory.data.dataclasses.Fish
import com.geras.fishistory.ui.ItemTouchHelperAdapter

class FishAdapter(private val onCLickAction: () -> Unit) : RecyclerView.Adapter<FishViewHolder>(),
    ItemTouchHelperAdapter {

    var fishList = mutableListOf<Fish>()

    fun replaceFishes(fish: List<Fish>) {
        fishList.clear()
        fishList.addAll(0, fish)
        notifyDataSetChanged()
    }

    fun sort(key: String) =
        when (key) {
            "name" -> {
                fishList.sortWith(
                    compareBy(
                        String.CASE_INSENSITIVE_ORDER,
                        { it.name.lowercase() })
                )
                notifyDataSetChanged()
            }
            "weight" -> {
                fishList.sortedBy { it.weight }
                notifyDataSetChanged()
            }
            "location" -> {
                fishList.sortWith(
                    compareBy(
                        String.CASE_INSENSITIVE_ORDER,
                        { it.location.lowercase() })
                )
                notifyDataSetChanged()
            }
            else -> fishList.shuffle()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FishViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = inflater.inflate(R.layout.recycler_item, parent, false)
        item.setOnClickListener { onCLickAction.invoke() }
        return FishViewHolder(item)
    }

    override fun onBindViewHolder(holder: FishViewHolder, position: Int) {
        val item = fishList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = fishList.size

    override fun onItemDismiss(position: Int) {
        fishList.removeAt(position)
        notifyItemRemoved(position)
    }

}

class FishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val fishPhoto: ImageView = itemView.findViewById(R.id.image_view)
    val fishDescription: TextView = itemView.findViewById(R.id.text_view)

    fun bind(fish: Fish) {
        fishDescription.text = fish.toString()
        fishPhoto.setImageResource(fish.photo)
    }
}
