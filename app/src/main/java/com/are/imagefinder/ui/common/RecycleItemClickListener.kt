package com.are.imagefinder.ui.common

import android.view.View
import com.are.imagefinder.data.model.Item

interface RecyclerItemClickListener {

    fun onItemClick(view: View, items: MutableList<Item>, position: Int)
}