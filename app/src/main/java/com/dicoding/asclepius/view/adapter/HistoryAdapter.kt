package com.dicoding.asclepius.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import com.dicoding.asclepius.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<CancerEntity, HistoryAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((CancerEntity) -> Unit)? = null
    var onItemDelete: ((CancerEntity) -> Unit)? = null

    inner class ListViewHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resultData: CancerEntity) {
            with(binding) {
                ivPreview.setImageURI(resultData.imageUri?.toUri())
                tvDateTaken.text = resultData.dateTaken
                ibDelete.setOnClickListener {
                    onItemDelete?.invoke(resultData)
                }
            }
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = getItem(position)
                    onItemClick?.invoke(clickedItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CancerEntity>() {
            override fun areItemsTheSame(oldItem: CancerEntity, newItem: CancerEntity): Boolean {
                return oldItem.classificationId == newItem.classificationId
            }

            override fun areContentsTheSame(oldItem: CancerEntity, newItem: CancerEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}