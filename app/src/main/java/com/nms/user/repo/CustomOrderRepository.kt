package com.nms.user.repo

import android.content.Context
import com.example.nmsadminapp.utils.api.ApiResponse
import com.google.gson.Gson
import com.nms.user.models.CustomOrderModel
import com.nms.user.service.Authentication
import com.nms.user.utils.api.ApiRequest

class CustomOrderRepository {

    companion object {
        // Function to add custom order
        fun addCustomOrder(context: Context, customOrderModel: CustomOrderModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_ADD_CUSTOM_ORDER,
                Gson().toJson(customOrderModel),
                Authentication.getToken(context)
            )
        }

        // Function to get custom orders
        fun getCustomOrdersByUserid(context: Context): ApiResponse {
            return ApiRequest.getRequest(
                ApiRequest.URL_GET_CUSTOM_ORDERS,
                Authentication.getToken(context)
            )
        }

        // Function to get custom order by id
        fun getCustomOrderById(context: Context, id: String): ApiResponse {
            return ApiRequest.getRequest(
                ApiRequest.URL_GET_CUSTOM_ORDER_BY_ID + id,
                Authentication.getToken(context)
            )
        }

        // Function to get custom order by user id
        fun getCustomOrderByUserId(context: Context): ApiResponse {
            return ApiRequest.getRequest(
                ApiRequest.URL_GET_CUSTOM_ORDER_BY_USERID,
                Authentication.getToken(context)
            )
        }

        // Function to update custom order
        fun updateCustomOrder(context: Context, customOrderModel: CustomOrderModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_UPDATE_CUSTOM_ORDER,
                Gson().toJson(customOrderModel),
                Authentication.getToken(context)
            )
        }

        // Function to delete custom order
        fun deleteCustomOrder(context: Context, customOrderModel: CustomOrderModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_DELETE_CUSTOM_ORDER,
                Gson().toJson(customOrderModel),
                Authentication.getToken(context)
            )
        }
    }
}