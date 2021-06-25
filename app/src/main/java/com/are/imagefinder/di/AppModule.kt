package com.are.imagefinder.di

import android.content.Context
import com.are.imagefinder.BuildConfig
import com.are.imagefinder.data.pref.AppPreferences
import com.are.imagefinder.data.remote.ImageFinderDataSource
import com.are.imagefinder.data.remote.net.ImageFinderApi
import com.are.imagefinder.data.repository.ImageFinderRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideGSON(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideAPIService(retrofit: Retrofit): ImageFinderApi = retrofit.create(ImageFinderApi::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: ImageFinderApi) = ImageFinderDataSource(api)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ImageFinderDataSource) = ImageFinderRepository(remoteDataSource)

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context) = AppPreferences(appContext)
}