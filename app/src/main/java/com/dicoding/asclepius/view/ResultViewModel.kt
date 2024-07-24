package com.dicoding.asclepius.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.source.remote.network.ApiResponse
import com.dicoding.asclepius.data.source.remote.response.NewsResponse
import com.dicoding.asclepius.domain.repository.AppRepository
import kotlinx.coroutines.launch

class ResultViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _newsResponse = MutableLiveData<ApiResponse<NewsResponse>>()
    val newsResponse get() = _newsResponse

    fun getTopHeadlineNews() {
        viewModelScope.launch {
            repository.getTopHeadlineNews().collect {
                _newsResponse.postValue(it)
            }
        }
    }
}