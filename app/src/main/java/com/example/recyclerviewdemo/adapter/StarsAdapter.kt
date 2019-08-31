package com.example.recyclerviewdemo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.R
import com.example.recyclerviewdemo.model.StarModel
import kotlinx.android.synthetic.main.item_star.view.*

class StarsAdapter(
    private var list: List<StarModel>,
    private val listener: OnItemClickedListener
) : RecyclerView.Adapter<StarsAdapter.VHStars>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHStars {
        return VHStars(
            LayoutInflater.from(parent.context).inflate(R.layout.item_star, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VHStars, position: Int) {
        with(holder) {
            btnStar.text = list[position].starName

            val isSelected = list[position].isSelected
            if (isSelected) {
                btnStar.setBackgroundColor(Color.parseColor("blue"))
            } else {
                btnStar.setBackgroundColor(Color.parseColor("green"))
            }

            btnStar.setOnClickListener {
                list[position].isSelected = !isSelected
                notifyItemChanged(position)
                listener.onStarItemClicked(list[position])
            }
        }
    }

    fun selectAllStar() {
        list = list.map {
            it.isSelected = true
            listener.onStarItemClicked(it)
            it
        }
        notifyDataSetChanged()
    }

    fun unselectAllStar() {
        list = list.map {
            it.isSelected = false
            listener.onStarItemClicked(it)
            it
        }
        notifyDataSetChanged()
    }


    class VHStars(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnStar: TextView = itemView.btnStar
    }

    interface OnItemClickedListener {
        fun onStarItemClicked(starModel: StarModel)
    }
}
