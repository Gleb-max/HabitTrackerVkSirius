package com.habit.tracker.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.habit.tracker.databinding.LayoutRequestCardBinding
import com.habit.tracker.domain.entity.Request

class RequestListAdapter : ListAdapter<Request, RequestItemViewHolder>(RequestItemDiffCallback()) {

    var onRequestItemClickListener: ((Request) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestItemViewHolder {
        val binding = LayoutRequestCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RequestItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestItemViewHolder, position: Int) {
        val requestItem = getItem(position)
        holder.bind(requestItem)
        val binding = holder.binding
        binding.root.setOnClickListener {
            onRequestItemClickListener?.invoke(requestItem)
        }
    }
}