package com.are.imagefinder.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item(

    val title: String,
    val link: String,
    val media: Media,
    @SerializedName("date_taken")
    val dateTaken: String,
    val description: String,
    val published: String,
    val author: String,
    @SerializedName("author_id")
    val authorId: String,
    val tags: String

): Serializable