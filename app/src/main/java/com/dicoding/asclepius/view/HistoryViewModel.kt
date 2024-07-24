package com.dicoding.asclepius.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import com.dicoding.asclepius.domain.repository.AppRepository
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: AppRepository
) : ViewModel() {

    fun getAllPredictionResult() = repository.getAllPredictionResult().asLiveData()

    fun deletePredictionResult(result: CancerEntity) {
        viewModelScope.launch {
            repository.deletePredictionResult(result)
        }
    }
}