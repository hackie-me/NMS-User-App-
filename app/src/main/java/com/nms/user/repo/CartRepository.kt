package com.nms.user.repo

import android.content.Context
import com.example.nmsadminapp.utils.api.ApiResponse
import com.google.gson.Gson
import com.nms.user.models.CartModel
import com.nms.user.service.Authentication
import com.nms.user.utils.api.ApiRequest

class CartRepository {
    companion object {
        // Add to cart
        fun addToCart(context: Context, cartModel: CartModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_ADD_CART_ITEM,
                Gson().toJson(cartModel),
                Authentication.getToken(context)
            )
        }

        // Get cart items
        fun getCartItems(context: Context): ApiResponse {
            return ApiRequest.getRequest(
                ApiRequest.URL_GET_CART_ITEMS,
                Authentication.getToken(context)
            )
        }

        // Delete cart item
        fun deleteCartItem(context: Context, cartModel: CartModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_DELETE_CART_ITEM,
                Gson().toJson(cartModel),
                Authentication.getToken(context)
            )
        }

        // Update cart item
        fun updateCartQuantity(context: Context, cartModel: CartModel): ApiResponse {
            return ApiRequest.postRequest(
                ApiRequest.URL_UPDATE_CART_QUANTITY,
                Gson().toJson(cartModel),
                Authentication.getToken(context)
            )
        }
    }
}