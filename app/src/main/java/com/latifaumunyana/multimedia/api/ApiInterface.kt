package com.latifaumunyana.multimedia.api

import com.latifaumunyana.multimedia.model.PhotoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
    @Multipart
    @POST("/selfie")
    suspend fun uploadPhoto(
        @Part caption: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ):Response<PhotoResponse>
}