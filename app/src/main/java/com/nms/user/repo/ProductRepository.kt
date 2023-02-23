package com.nms.user.repo

import android.content.Context
import com.example.nmsadminapp.utils.api.ApiRequest
import com.example.nmsadminapp.utils.api.ApiResponse
import com.nms.user.database.DBHelper
import com.nms.user.utils.Helper

class ProductRepository {
    companion object {
        // Function to get all Products
        fun getAllProducts(): ApiResponse {
            return ApiRequest.getRequest(ApiRequest.URL_GET_PRODUCTS)
        }

        // Function to get Product by id
        fun getProductById(id: String): ApiResponse {
            return ApiRequest.getRequest(ApiRequest.URL_GET_PRODUCT_BY_ID + id)
        }
    }
}