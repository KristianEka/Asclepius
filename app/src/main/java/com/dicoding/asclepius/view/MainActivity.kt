package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.source.local.entity.CancerEntity
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.yalantis.ucrop.UCrop
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.apply {
            galleryButton.setOnClickListener { startGallery() }
            analyzeButton.setOnClickListener { analyzeImage() }
            appBar.ibHistory.setOnClickListener {
                val intent = Intent(this@MainActivity, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            cropImage(uri)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    private fun cropImage(sourceUri: Uri) {
        val fileName = "Asclepius_CroppedImage_${System.currentTimeMillis()}"
        val destinationUri = Uri.fromFile(File(cacheDir, fileName))
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .start(this, launcherCrop)
    }

    private val launcherCrop = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val resultUri: Uri = UCrop.getOutput(result.data!!)!!
            currentImageUri = resultUri
            showImage()
        } else {
            val cropError = UCrop.getError(result.data!!)
            cropError?.printStackTrace()
        }
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        currentImageUri?.let { uri ->
            Log.d("ImageURI", "showImage: $uri")
            binding.previewImageView.setImageURI(uri)
        }
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.

        if (currentImageUri != null) {
            binding.progressIndicator.visibility = View.VISIBLE

            val imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        binding.progressIndicator.visibility = View.GONE
                        showToast(error)
                    }

                    override fun onResults(results: List<Classifications>?) {
                        runOnUiThread {
                            results?.let {
                                if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                    val classificationResult = it.first().categories.first()

                                    if (currentImageUri != null) {
                                        binding.progressIndicator.visibility = View.GONE

                                        val time = Calendar.getInstance().time
                                        val formatter =
                                            SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss", Locale.US)
                                        val currentTime = formatter.format(time)

                                        moveToResult(
                                            CancerEntity(
                                                imageUri = currentImageUri.toString(),
                                                predictionResult = classificationResult.label,
                                                confidenceScore = classificationResult.score,
                                                dateTaken = currentTime.toString()
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
            imageClassifierHelper.classifyStaticImage(currentImageUri!!)
        } else {
            showToast(getString(R.string.please_insert_the_image_file_first))
        }
    }

    private fun moveToResult(cancerEntity: CancerEntity) {
        viewModel.insertPredictionResult(cancerEntity)
        showToast(getString(R.string.the_classification_result_has_been_saved))
        binding.previewImageView.setImageResource(R.drawable.ic_place_holder)
        currentImageUri = null
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_CLASSIFICATION_RESULT, cancerEntity)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}