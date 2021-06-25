package com.are.imagefinder.data.remote

import com.are.imagefinder.data.remote.net.ImageFinderApi
import javax.inject.Inject

class ImageFinderDataSource @Inject constructor(private val api: ImageFinderApi): BaseDataSource() {

    suspend fun searchByTag(tag: String) = getResult {
        api.getSearchPictureByTag(tag)
    }
}