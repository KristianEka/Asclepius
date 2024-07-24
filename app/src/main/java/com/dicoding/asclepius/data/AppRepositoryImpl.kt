package com.dicoding.asclepius.data

import android.content.Context
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import com.dicoding.asclepius.data.source.local.room.CancerDao
import com.dicoding.asclepius.data.source.remote.network.ApiResponse
import com.dicoding.asclepius.data.source.remote.network.ApiService
import com.dicoding.asclepius.data.source.remote.response.NewsResponse
import com.dicoding.asclepius.domain.repository.AppRepository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class AppRepositoryImpl(
    private val cancerDao: CancerDao,
    private val apiService: ApiService,
    private val context: Context
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

    override suspend fun getTopHeadlineNews(): Flow<ApiResponse<NewsResponse>> {
        return flow {
            emit(ApiResponse.Loading)
            try {
                val response = apiService.getTopHeadlineNews()
                val dataArray = response.articles
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string()
                val errorResponse = errorMessage?.let {
                    try {
                        Gson().fromJson(it, NewsResponse::class.java)?.message
                    } catch (jsonException: JsonSyntaxException) {
                        null
                    }
                }
                emit(
                    ApiResponse.Error(
                        errorResponse
                            ?: context.getString(R.string.failed_to_display_top_headline_news)
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}