package com.nms.user.repo

import com.example.nmsadminapp.utils.api.ApiRequest
import com.example.nmsadminapp.utils.api.ApiResponse

class CategoryRepository {
    companion object{
        // Function to get all Categories
        fun getAllCategories() : ApiResponse {
            return ApiRequest.getRequest(ApiRequest.URL_GET_CATEGORIES)
        }
    }
}