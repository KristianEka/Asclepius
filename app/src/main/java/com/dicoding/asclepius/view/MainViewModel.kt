package com.dicoding.asclepius.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import com.dicoding.asclepius.domain.repository.AppRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: AppRepository
) : ViewModel() {

    fun insertPredictionResult(result: CancerEntity) {
        viewModelScope.launch {
            repository.insertPredictionResult(result)
        }
    }
}