package com.nms.user.repo

import android.content.Context
import com.example.nmsadminapp.utils.api.ApiRequest
import com.example.nmsadminapp.utils.api.ApiResponse
import com.google.gson.Gson
import com.nms.user.models.UserModel

class AuthRepository {
    companion object{
        // Function to Register new User
        fun registerUser(userModel: UserModel, context: Context) : ApiResponse {
            return ApiRequest.postRequest(ApiRequest.URL_REGISTER, Gson().toJson(userModel))
        }

        // Function to Login User
        fun loginUser(userModel: UserModel, context: Context) : ApiResponse {
            return ApiRequest.postRequest(ApiRequest.URL_LOGIN, Gson().toJson(userModel))
        }
    }
}