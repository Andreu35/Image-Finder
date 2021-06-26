package com.are.imagefinder.ui.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.are.imagefinder.data.model.HomeResponse
import com.are.imagefinder.data.repository.ImageFinderRepository
import com.are.imagefinder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ImageFinderRepository) : ViewModel() {

    private var _homeListSuccessCallback = MutableLiveData<Resource<HomeResponse>>()
    val homeList: LiveData<Resource<HomeResponse>> = _homeListSuccessCallback

    /**
     * Search Pictures from Tags
     * @param tags Query Text
     */
    fun searchPictureByTags(tags: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _homeListSuccessCallback.postValue(repository.searchPictureByTag(tags).value)
        }
    }

    /**
     * Search Recent Uploaded Pictures
     */
    fun searchRecentUploadedPictures() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeListSuccessCallback.postValue(repository.searchRecentUploadedPictures().value)
        }
    }
}