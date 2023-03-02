package com.nms.user.repo

import com.example.nmsadminapp.utils.api.ApiResponse
import com.google.gson.Gson
import com.nms.user.models.UserModel
import com.nms.user.utils.api.ApiRequest

class AuthRepository {
    companion object{
        // Function to Register new User
        fun registerUser(userModel: UserModel): ApiResponse {
            return ApiRequest.postRequest(ApiRequest.URL_REGISTER, Gson().toJson(userModel))
        }

        // Function to Login User
        fun loginUser(userModel: UserModel): ApiResponse {
            return ApiRequest.postRequest(ApiRequest.URL_LOGIN, Gson().toJson(userModel))
        }
    }
}