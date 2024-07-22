package com.dicoding.asclepius.domain.repository

import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getAllPredictionResult(): Flow<List<CancerEntity>>

    suspend fun insertPredictionResult(result: CancerEntity)

    suspend fun deletePredictionResult(result: CancerEntity)
}