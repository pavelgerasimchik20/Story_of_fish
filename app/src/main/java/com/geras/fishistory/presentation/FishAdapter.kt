package com.geras.fishistory.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.geras.fishistory.R
import com.geras.fishistory.data.Fish
import java.io.File

class FishAdapter(
    private val onDeleteItem: (fish: Fish) -> Unit,
    private val onPictureClickAction: (fish: Fish) -> Unit,
    private val onDescriptionClickAction: (fish: Fish) -> Unit
) : RecyclerView.Adapter<FishViewHolder>(),
    SimpleItemTouchHelperCallback.ItemTouchHelperDismissCallback {

    var fishList = mutableListOf<Fish>()

    override fun onItemDismiss(position: Int) {
        if (position >= 0 && position <= fishList.size - 1)
            onDeleteItem(fishList[position])
        fishList.removeAt(position)
        notifyItemRemoved(position)
    }

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
                fishList.sortBy { it.weight }
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
        val holder = FishViewHolder(item, onPictureClickAction, onDescriptionClickAction)
        return holder
    }

    override fun onBindViewHolder(holder: FishViewHolder, position: Int) {
        val item = fishList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = fishList.size
}

class FishViewHolder(
    itemView: View,
    private val onPictureClickAction: (fish: Fish) -> Unit,
    private val onDescriptionClickAction: (fish: Fish) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    private val fishPhoto: ImageView = itemView.findViewById(R.id.iv_item_of_list)
    private val fishDescription: TextView = itemView.findViewById(R.id.tv_item_of_list)

    fun bind(fish: Fish) {

        fishDescription.text = fish.toString()
        val photoFile = fish.photoPath?.let { File(it) }
        fishPhoto.load(photoFile) {
            placeholder(R.drawable.ic_baseline_accessibility_new_24)
        }

        fishPhoto.setOnClickListener {
            onPictureClickAction.invoke(fish)
        }

        fishDescription.setOnClickListener {
            onDescriptionClickAction.invoke(fish)
        }
    }
}
