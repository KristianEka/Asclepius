package com.dicoding.asclepius.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val resultData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_CLASSIFICATION_RESULT, CancerEntity::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_CLASSIFICATION_RESULT)
        }

        if (resultData != null) {
            binding.apply {
                resultImage.setImageURI(resultData.imageUri?.toUri())

                val displayResult = "Result: ${resultData.predictionResult} " +
                        NumberFormat.getPercentInstance().format(resultData.confidenceScore)
                            .trim() + "\nTime taken: ${resultData.dateTaken}"
                resultText.text = displayResult
            }
        }
    }

    companion object {
        const val EXTRA_CLASSIFICATION_RESULT = "extra_classification_result"
    }
}