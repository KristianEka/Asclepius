package com.dicoding.asclepius.data.source.local.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CancerEntity(
    var imageUri: Uri? = null,
    var prediction: String? = null,
    var confidenceScore: Float? = 0F,
) : Parcelable