package com.dicoding.asclepius.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CancerDao {

    @Query("SELECT * FROM cancer")
    fun getAllPredictionResult(): Flow<List<CancerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPredictionResult(result: CancerEntity)

    @Delete
    suspend fun deletePredictionResult(result: CancerEntity)
}