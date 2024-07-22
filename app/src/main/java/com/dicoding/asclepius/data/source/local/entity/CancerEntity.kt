package com.dicoding.asclepius.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cancer")
@Parcelize
data class CancerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "classificationId")
    val classificationId: Int? = null,

    @ColumnInfo(name = "imageUri")
    val imageUri: String? = null,

    @ColumnInfo(name = "predictionResult")
    val predictionResult: String? = null,

    @ColumnInfo(name = "confidenceScore")
    val confidenceScore: Float? = 0F,

    @ColumnInfo(name = "dateTaken")
    val dateTaken: String? = null
) : Parcelable