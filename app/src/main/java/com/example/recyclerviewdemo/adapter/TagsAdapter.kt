package com.example.recyclerviewdemo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.R
import com.example.recyclerviewdemo.model.TagModel
import kotlinx.android.synthetic.main.item_tag.view.*

class TagsAdapter(val list: List<TagModel>,
                  val listener: OnItemClickListener) : RecyclerView.Adapter<TagsAdapter.VHTags>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHTags {
        return VHTags(
                LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VHTags, position: Int) {
        with(holder) {
            btnTag.text = list[position].tagName

            val isSelected = list[position].isSelected
            if (isSelected) {
                btnTag.setBackgroundColor(Color.parseColor("blue"))
            } else {
                btnTag.setBackgroundColor(Color.parseColor("green"))
            }

            itemView.setOnClickListener {
                list[position].isSelected = !isSelected
                notifyItemChanged(position)
                listener.onTagItemClicked(list[position])
            }
        }
    }

    class VHTags(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnTag: TextView = itemView.btnTag
    }

    interface OnItemClickListener {
        fun onTagItemClicked(tagModel: TagModel)
    }
}