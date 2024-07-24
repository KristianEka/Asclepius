package com.dicoding.asclepius.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import com.dicoding.asclepius.data.source.remote.network.ApiResponse
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view.adapter.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var newsAdapter: NewsAdapter
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

        showHeadlineNews()
    }

    private fun showHeadlineNews() {
        newsAdapter = NewsAdapter()
        binding.apply {
            rvNews.layoutManager = LinearLayoutManager(
                this@ResultActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvNews.setHasFixedSize(true)
            rvNews.adapter = newsAdapter
        }
        viewModel.apply {
            getTopHeadlineNews()
            newsResponse.observe(this@ResultActivity) { result ->
                when (result) {
                    is ApiResponse.Loading -> {
                        stateLoading(true)
                        stateEmpty(false)
                    }

                    is ApiResponse.Empty -> {
                        stateLoading(false)
                        stateEmpty(true)
                    }

                    is ApiResponse.Error -> {
                        stateLoading(false)
                        stateEmpty(false)
                        Toast.makeText(
                            this@ResultActivity,
                            result.errorMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is ApiResponse.Success -> {
                        stateLoading(false)
                        if (result.data.articles.isNullOrEmpty()) {
                            stateEmpty(true)
                        } else {
                            newsAdapter.submitList(result.data.articles)
                        }
                    }
                }
            }
        }
    }

    private fun stateLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun stateEmpty(isEmpty: Boolean) {
        binding.apply {
            viewEmpty.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }

    companion object {
        const val EXTRA_CLASSIFICATION_RESULT = "extra_classification_result"
    }
}