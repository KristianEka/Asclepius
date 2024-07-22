package com.dicoding.asclepius.data

import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import com.dicoding.asclepius.data.source.local.room.CancerDao
import com.dicoding.asclepius.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    private val cancerDao: CancerDao
) : AppRepository {
    override fun getAllPredictionResult(): Flow<List<CancerEntity>> {
        return cancerDao.getAllPredictionResult()
    }

    override suspend fun insertPredictionResult(result: CancerEntity) {
        cancerDao.insertPredictionResult(result)
    }

    override suspend fun deletePredictionResult(result: CancerEntity) {
        cancerDao.deletePredictionResult(result)
    }
}