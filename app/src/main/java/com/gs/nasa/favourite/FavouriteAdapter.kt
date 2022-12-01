package com.gs.nasa.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gs.nasa.R
import com.gs.nasa.databinding.ItemFavouriteBinding
import com.gs.nasa.favourite.FavouriteAdapter.FavouriteViewHolder
import com.gs.nasa.pod.data.model.Data

class FavouriteAdapter : RecyclerView.Adapter<FavouriteViewHolder>() {
    var onItemClick: ((Data) -> Unit)? = null
    private var reviewList = listOf<Data>()

    fun setReviews(list: List<Data>) {
        this.reviewList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavouriteBinding.inflate(inflater, parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val item = reviewList[position]
        holder.binding.date.text = item.date
        holder.binding.title.text = item.title
        holder.binding.description.text = item.explanation

        Glide.with(holder.binding.image.context)
            .load(item.url)
            .placeholder(R.drawable.ic_download)
            .error(R.drawable.ic_download)
            .into(holder.binding.image);
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }


    inner class FavouriteViewHolder(val binding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(reviewList[adapterPosition])
            }
        }
    }
}