package com.are.imagefinder.data.model

import java.io.Serializable

data class HomeResponse(

    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: MutableList<Item>

): Serializable