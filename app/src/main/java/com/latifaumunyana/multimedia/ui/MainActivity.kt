package com.latifaumunyana.multimedia.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import com.latifaumunyana.multimedia.R
import com.latifaumunyana.multimedia.databinding.ActivityMainBinding
import com.latifaumunyana.multimedia.model.PhotoResponse
import com.latifaumunyana.multimedia.utils.Constants
import com.latifaumunyana.multimedia.viewModel.PhotosViewModel
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    val photosViewModel: PhotosViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    var photoUri: Uri ?= null
    var caption: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri ->
            if(uri != null) {
                photoUri = uri
            }
        }

        binding.btnUpload.setOnClickListener {
            validateForm()
        }
    }

    override fun onResume(){
        super.onResume()
        binding.ivPhoto.setOnClickListener{
            val mimeType = "image/*"
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
        }

        photosViewModel.errorLiveData.observe(this){ error->
            Toast.makeText(this, error, Toast.LENGTH_LONG)
        }

        photosViewModel.photoResponseLivedata.observe(this){  photoResponse->
            val imageUrl = "${Constants.BASEURL}${photoResponse.image}"
            Picasso.get()
                .load(imageUrl)
                .into(binding.ivPhoto)
        }
    }

    private fun validateForm() {
        var err = false
        if (photoUri == null) {
            err = true
            Toast.makeText(this, "Please select a Photo", Toast.LENGTH_LONG).show()
        }

        val caption = binding.etCaption.text.toString()
        if (caption.isBlank()){
            err = true
            binding.etCaption.error = "Caption required"
        }

        if (!err) {
            photosViewModel.postPhoto(photoUri!!, caption!!)
        }

    }

}