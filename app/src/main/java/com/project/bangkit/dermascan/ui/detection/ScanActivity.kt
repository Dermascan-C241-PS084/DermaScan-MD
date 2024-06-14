package com.project.bangkit.dermascan.ui.detection

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.project.bangkit.dermascan.R
import com.project.bangkit.dermascan.databinding.ActivityScanBinding
import com.project.bangkit.dermascan.response.Data
import com.project.bangkit.dermascan.ui.getImageUri
import com.project.bangkit.dermascan.ui.main.MainActivity
import com.project.bangkit.dermascan.ui.reduceFileImage
import com.project.bangkit.dermascan.ui.uriToFile
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.Serializable

@Suppress("DEPRECATION")
class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private var dialogLoading: Dialog? = null
    private val viewModel: ScanViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                // Handle permission request denial
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.btnGaleri.setOnClickListener { startGallery() }
        binding.btnKamera.setOnClickListener { startCamera() }
        binding.btnAnalyze.setOnClickListener {
            if (!isNetworkAvailable()) {
                Toast.makeText(this, "Check Your Connection", Toast.LENGTH_SHORT).show()
            } else if (viewModel.currentImageUri == null) {
                Toast.makeText(this, "Enter Your Picture to Start Analyze", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.currentImageUri?.let { uri ->
                    showToast("Analyzing image...")
                    val imageFile = uriToFile(uri, this).reduceFileImage()
                    val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                    val multipartBody = MultipartBody.Part.createFormData(
                        "image",
                        imageFile.name,
                        requestImageFile
                    )
                    viewModel.uploadImage(multipartBody)
                }
            }
        }

        binding.btnBack.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        viewModel.uploadImageResponse.observe(this) { response ->
            response?.data?.let { data ->
                if (data is Data) {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("data", data as Serializable)
                    intent.putExtra("imagePath", viewModel.currentImageUri.toString()) // Add this line
                    startActivity(intent)
                }
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.currentImageUri = uri
            startCrop(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        viewModel.currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(viewModel.currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            viewModel.currentImageUri?.let { uri ->
                startCrop(uri)
            }
        }
    }

    private fun startCrop(uri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "cropped"))
        val options = UCrop.Options()
        options.setCompressionQuality(80)

        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(450, 450)
            .withOptions(options)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            viewModel.currentImageUri = resultUri
            showImage() // Refresh the image view with the latest cropped image
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Log.e("uCrop Error", "Crop error: $cropError")
        }
    }

    private fun showImage() {
        viewModel.currentImageUri?.let { uri ->
            Log.d("Image URI", "showImage: $uri")
            binding.ivImagePrivie.setImageURI(null) // Clear the current image
            binding.ivImagePrivie.setImageURI(uri) // Load the new image
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            if (dialogLoading == null) {
                dialogLoading = Dialog(this)
                dialogLoading!!.setContentView(R.layout.loading_dialog)
                dialogLoading!!.window!!.setLayout(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                dialogLoading!!.show()
            }
        } else {
            dialogLoading?.dismiss()
            dialogLoading = null
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}