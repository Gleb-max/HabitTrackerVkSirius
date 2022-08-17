package com.habit.tracker.presentation.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.habit.tracker.databinding.LayoutRequestCardBinding
import com.habit.tracker.domain.entity.Request

class RequestItemViewHolder(
    val binding: LayoutRequestCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(requestItem: Request) {
        Glide.with(binding.root.context).load(requestItem.photos[0]).into(binding.ivPhoto)
        binding.tvTitle.text = requestItem.title
        binding.tvDescription.text = requestItem.description
    }
}