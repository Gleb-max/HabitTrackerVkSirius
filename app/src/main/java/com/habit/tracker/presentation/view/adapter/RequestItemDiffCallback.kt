package com.habit.tracker.presentation.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.habit.tracker.domain.entity.Request

class RequestItemDiffCallback : DiffUtil.ItemCallback<Request>() {
    override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean {
        return oldItem == newItem
    }
}