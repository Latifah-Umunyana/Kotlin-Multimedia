package com.latifaumunyana.multimedia.repository

import com.latifaumunyana.multimedia.api.ApiClient
import com.latifaumunyana.multimedia.api.ApiInterface
import com.latifaumunyana.multimedia.model.LoginRequest
import com.latifaumunyana.multimedia.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginRepository {
    val apiClient = ApiClient.buildApiClient(ApiInterface::class.java)
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>{
        return withContext(Dispatchers.IO){
            apiClient.login(loginRequest)
        }
    }
}