package com.are.imagefinder.extensions

import android.view.View

fun View.goneUnless(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}