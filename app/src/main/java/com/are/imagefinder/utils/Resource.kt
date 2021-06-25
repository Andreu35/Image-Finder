package com.are.imagefinder.utils

import androidx.lifecycle.MutableLiveData

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): MutableLiveData<Resource<T>> {
            return MutableLiveData(Resource(Status.SUCCESS, data, null))
        }

        fun <T> error(message: String, data: T? = null): MutableLiveData<Resource<T>> {
            return MutableLiveData(Resource(Status.ERROR, data, message))
        }

        fun <T> loading(data: T? = null): MutableLiveData<Resource<T>> {
            return MutableLiveData(Resource(Status.LOADING, data, null))
        }
    }
}