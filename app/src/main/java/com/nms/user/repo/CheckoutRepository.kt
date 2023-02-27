package com.nms.user.repo

import com.example.nmsadminapp.utils.api.ApiRequest
import com.example.nmsadminapp.utils.api.ApiResponse
import com.google.gson.Gson
import com.nms.user.models.CheckoutModel

class CheckoutRepository {
    companion object {
        fun placeOrder(checkoutModel: CheckoutModel): ApiResponse {
            return ApiRequest.postRequest(ApiRequest.URL_ADD_ORDER, Gson().toJson(checkoutModel))
        }
    }
}