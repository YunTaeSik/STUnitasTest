package com.example.stunitastest.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.stunitastest.R
import com.example.stunitastest.databinding.SearchItemBinding
import com.example.stunitastest.entity.Document

class SearchAdapter : ListAdapter<Document, SearchAdapter.SearchViewHolder>(
    PrintHistoryDiffUtil()
) {

    class PrintHistoryDiffUtil : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(
            oldItem: Document,
            newItem: Document
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Document,
            newItem: Document
        ): Boolean {
            return if (oldItem != null && newItem != null) {
                ((oldItem.collection == newItem.collection) && (oldItem.thumbnail_url == newItem.thumbnail_url) && (oldItem.image_url == newItem.image_url) && (oldItem.width == newItem.width)
                        && (oldItem.height == newItem.height) && (oldItem.display_sitename == newItem.display_sitename) && (oldItem.doc_url == newItem.doc_url) && (oldItem.datetime == newItem.datetime))
            } else {
                false
            }
        }
    }

    inner class SearchViewHolder(var binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Document) {
            binding.data = data

            Glide.with(binding.root.context).load(data.image_url).thumbnail(0.1f)
                .error(R.drawable.img_error)
                .signature(ObjectKey(data))
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val item = DataBindingUtil.inflate<SearchItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_search,
            parent,
            false
        )
        return SearchViewHolder(item)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}