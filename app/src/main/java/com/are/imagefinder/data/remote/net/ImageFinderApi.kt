package com.are.imagefinder.data.remote.net

import com.are.imagefinder.data.model.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageFinderApi {

    @GET
    suspend fun getSearchPictureByTag(
        @Path("tag") key: String): Response<HomeResponse>
}