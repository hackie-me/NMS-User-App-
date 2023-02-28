package com.nms.user.repo

import android.content.Context
import com.example.nmsadminapp.utils.api.ApiResponse
import com.google.gson.Gson
import com.nms.user.models.AddressModel
import com.nms.user.service.Authentication
import com.nms.user.utils.api.ApiRequest

class AddressRepository {
    companion object {

        // Function to get all address
        fun getAllAddress(context: Context): ApiResponse {
            return ApiRequest.getRequest(
                ApiRequest.URL_GET_ADDRESSES,
                Authentication.getToken(context)
            )
        }

        // Function to add address
        fun addAddress(context: Context, addressModel: AddressModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_ADD_ADDRESS,
                Gson().toJson(addressModel),
                Authentication.getToken(context)
            )
        }

        // Function to update address
        fun updateAddress(context: Context, addressModel: AddressModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_UPDATE_ADDRESS,
                Gson().toJson(addressModel),
                Authentication.getToken(context)
            )
        }

        // Function to delete address
        fun deleteAddress(context: Context, addressModel: AddressModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_DELETE_ADDRESS,
                Gson().toJson(addressModel),
                Authentication.getToken(context)
            )
        }
    }
}