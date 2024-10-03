package com.latifaumunyana.multimedia.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.latifaumunyana.multimedia.model.PhotoResponse
import com.latifaumunyana.multimedia.repository.PhotoRepository
import kotlinx.coroutines.launch

class PhotosViewModel: ViewModel() {

    val photoRepo = PhotoRepository()
    val errorLiveData = MutableLiveData<String>()
    val photoResponseLivedata = MutableLiveData<PhotoResponse>()

    fun postPhoto(uri: Uri, caption: String){
        viewModelScope.launch {
            val response = photoRepo.uploadPhoto(uri, caption)
            if (response.isSuccessful){
                photoResponseLivedata.postValue(response.body())
            }

            else{
                errorLiveData.postValue(response.errorBody()?.string())
            }
        }
    }
}