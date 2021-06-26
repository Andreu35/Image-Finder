package com.are.imagefinder.ui.features.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.are.imagefinder.R
import com.are.imagefinder.data.model.Item
import com.are.imagefinder.databinding.DetailsListItemBinding
import com.bumptech.glide.Glide

class DetailsAdapter(private val items: MutableList<Item>
) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    var itemClickListener: (view: View, items: MutableList<Item>, position: Int) -> Unit = { _, _, _ -> }

    init {
        setHasStableIds(true)
    }

    /**
     * Get current total items in the list
     */
    override fun getItemCount()= items.count()

    /**
     * Get the current Item ID from position
     * @param position Item position
     *
     * Return a unique ID to prevent repeat items
     */
    override fun getItemId(position: Int): Long = items[position].authorId.hashCode().toLong()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.details_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    /**
     * Custom ViewHolder class for items
     *
     * @param view View
     */
    inner class ViewHolder constructor(view: View?) : RecyclerView.ViewHolder(view!!) {
        private val binding: DetailsListItemBinding? = DataBindingUtil.bind(view!!)

        fun bindItems(item: Item) {
            binding!!.root.setOnClickListener{
                itemClickListener(binding.image, items, adapterPosition)
            }

            Glide.with(itemView.context)
                .load(item.media.m)
                .into(binding.image)
        }

    }
}