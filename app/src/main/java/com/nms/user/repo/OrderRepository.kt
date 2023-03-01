package com.nms.user.repo

import android.content.Context
import com.example.nmsadminapp.utils.api.ApiResponse
import com.google.gson.Gson
import com.nms.user.models.OrderModel
import com.nms.user.service.Authentication
import com.nms.user.utils.api.ApiRequest

class OrderRepository {
    companion object {
        // Function to Place Order
        fun placeOrder(context: Context, order: OrderModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_ADD_ORDER,
                Gson().toJson(order),
                Authentication.getToken(context)
            )
        }

        // Function to Get All Orders
        fun getAllOrders(context: Context): ApiResponse {
            return ApiRequest.getRequest(
                ApiRequest.URL_GET_ALL_ORDERS,
                Authentication.getToken(context)
            )
        }

        // Function to Cancel Order
        fun cancelOrder(context: Context, order: OrderModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_CANCEL_ORDER,
                Gson().toJson(order),
                Authentication.getToken(context)
            )
        }
    }
}