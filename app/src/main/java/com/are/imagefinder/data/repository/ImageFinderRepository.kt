package com.are.imagefinder.data.repository

import com.are.imagefinder.data.remote.ImageFinderDataSource
import javax.inject.Inject

class ImageFinderRepository @Inject constructor(private val remoteDataSource: ImageFinderDataSource) {

    // Remote
    suspend fun searchRecentUploadedPictures() = remoteDataSource.searchRecentUploadedPictures()
    suspend fun searchPictureByTag(tag: String) = remoteDataSource.searchPictureByTag(tag)
}