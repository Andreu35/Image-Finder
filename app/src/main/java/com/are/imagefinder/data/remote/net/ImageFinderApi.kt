package com.are.imagefinder.data.remote.net

import com.are.imagefinder.data.model.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageFinderApi {

    @GET("photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getSearchPicture(): Response<HomeResponse>

    @GET("photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getSearchPictureByTag(
        @Query("tags") key: String): Response<HomeResponse>
}