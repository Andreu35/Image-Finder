package com.are.imagefinder.data.remote

import com.are.imagefinder.data.remote.net.ImageFinderApi
import javax.inject.Inject

class ImageFinderDataSource @Inject constructor(private val api: ImageFinderApi): BaseDataSource() {

    suspend fun searchRecentUploadedPictures() = getResult {
        api.getSearchPicture()
    }

    suspend fun searchPictureByTag(tag: String) = getResult {
        api.getSearchPictureByTag(tag)
    }
}