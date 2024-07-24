package com.dicoding.asclepius.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.view.adapter.HistoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        showResultList()
    }

    private fun setupAdapter() {
        historyAdapter = HistoryAdapter()
        binding.apply {
            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            rvHistory.setHasFixedSize(true)
            rvHistory.adapter = historyAdapter
        }
    }

    private fun showResultList() {
        viewModel.apply {
            getAllPredictionResult().observe(this@HistoryActivity) { resultList ->
                if (resultList != null) {
                    if (resultList.isEmpty()) {
                        binding.viewEmpty.root.visibility = View.VISIBLE
                    } else {
                        binding.viewEmpty.root.visibility = View.GONE
                    }
                    historyAdapter.submitList(resultList)
                    historyAdapter.onItemClick = { selectedData ->
                        val intent = Intent(this@HistoryActivity, ResultActivity::class.java)
                        intent.putExtra(ResultActivity.EXTRA_CLASSIFICATION_RESULT, selectedData)
                        startActivity(intent)
                    }
                    historyAdapter.onItemDelete = { selectedData ->
                        deletePredictionResult(selectedData)
                        Toast.makeText(
                            this@HistoryActivity,
                            getString(R.string.the_classification_result_has_been_deleted),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}