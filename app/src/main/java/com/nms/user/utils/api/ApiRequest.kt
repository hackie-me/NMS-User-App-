package com.nms.user.utils.api

import com.example.nmsadminapp.utils.api.ApiResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ApiRequest {
    companion object {

        // private const val BASE_URL = "http://10.1.51.154/nms/api/" // College IP
        // private const val BASE_URL = "http://192.168.1.2/nms/api/" // Home IP

         private const val BASE_URL = "https://hardik.works/nms/api/" // Personal IP
        // Endpoints for AuthRepository
        const val URL_LOGIN = "${BASE_URL}auth/login.php"
        const val URL_REGISTER = "${BASE_URL}auth/register.php"
        const val URL_FORGOT_PASSWORD = "${BASE_URL}auth/forgot_password.php"
        const val URL_CHANGE_PASSWORD = "${BASE_URL}auth/change_password.php"

        // Endpoints for Handling categories
        const val URL_GET_CATEGORIES = "${BASE_URL}category/fetch.php"

        // Endpoints for Handling addresses
        const val URL_GET_ADDRESSES = "${BASE_URL}address/fetch.php"
        const val URL_ADD_ADDRESS = "${BASE_URL}address/insert.php"
        const val URL_UPDATE_ADDRESS = "${BASE_URL}address/update.php"
        const val URL_DELETE_ADDRESS = "${BASE_URL}address/delete.php"

        // Endpoints for Handling products
        const val URL_GET_PRODUCTS = "${BASE_URL}product/fetch.php"
        const val URL_GET_PRODUCT_BY_ID = "${BASE_URL}product/fetch_by_id.php?id="

        // Endpoints for Handling orders
        const val URL_GET_ALL_ORDERS = "${BASE_URL}order/fetch.php"
        const val URL_ADD_ORDER = "${BASE_URL}order/insert.php"
        const val URL_UPDATE_ORDER = "${BASE_URL}order/update.php"
        const val URL_CANCEL_ORDER = "${BASE_URL}order/cancel_order.php"

        // Endpoints for Handling custom orders
        const val URL_GET_CUSTOM_ORDERS = "${BASE_URL}custom_order/fetch.php"
        const val URL_ADD_CUSTOM_ORDER = "${BASE_URL}custom_order/insert.php"
        const val URL_UPDATE_CUSTOM_ORDER = "${BASE_URL}custom_order/update.php"
        const val URL_DELETE_CUSTOM_ORDER = "${BASE_URL}custom_order/delete.php"
        const val URL_GET_CUSTOM_ORDER_BY_ID = "${BASE_URL}custom_order/fetch_by_id.php?id="
        const val URL_GET_CUSTOM_ORDER_BY_USERID =
            "${BASE_URL}custom_order/fetch_by_user_id.php?user_id="

        // Endpoints for Handling cart
        const val URL_GET_CART_ITEMS = "${BASE_URL}cart/fetch.php"
        const val URL_ADD_CART_ITEM = "${BASE_URL}cart/insert.php"
        const val URL_UPDATE_CART_QUANTITY = "${BASE_URL}cart/update_quantity.php"
        const val URL_DELETE_CART_ITEM = "${BASE_URL}cart/delete.php"

        // Endpoints for Handling reviews
        const val URL_GET_REVIEWS = "${BASE_URL}review/fetch.php"
        const val URL_ADD_REVIEW = "${BASE_URL}review/insert.php"
        const val URL_UPDATE_REVIEW = "${BASE_URL}review/update.php"
        const val URL_DELETE_REVIEW = "${BASE_URL}review/delete.php"

        // Endpoints for Handling Contact Us
        const val URL_GET_CONTACT_US = "${BASE_URL}contact/fetch.php"
        const val URL_ADD_CONTACT_US = "${BASE_URL}contact/insert.php"
        const val URL_UPDATE_CONTACT_US = "${BASE_URL}contact/update.php"
        const val URL_DELETE_CONTACT_US = "${BASE_URL}contact/delete.php"

        // Endpoints for Handling Offers
        const val URL_GET_OFFERS = "${BASE_URL}offer/fetch.php"

        // Endpoints for Handling FAQ
        const val URL_GET_FAQ = "${BASE_URL}faq/fetch.php"

        // Endpoints for Handling Notifications
        const val URL_GET_NOTIFICATIONS = "${BASE_URL}notification/fetch.php"

        // Endpoints for Handling Upload Image
        const val URL_UPLOAD_CATEGORY_IMAGE = "${BASE_URL}store/upload_category_image.php"

        // Method to send get request
        fun getRequest(url: String, token: String? = null): ApiResponse {
            // Remove extra double quotes from token
            return if (token == null) {
                send(
                    Request.Builder()
                        .url(url)
                        .build()
                )
            } else {
                send(
                    Request.Builder()
                        .url(url)
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                )
            }
        }

        // Method to send post request
        fun postRequest(url: String, body: String, token: String? = null): ApiResponse {
            return if (token == null) {
                send(Request.Builder().url(url).post(body.toRequestBody()).build())
            } else {
                send(
                    Request.Builder()
                        .url(url)
                        .post(body.toRequestBody())
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                )
            }
        }

        // Post Request with image
        fun postRequestWithImage(url: String, bodyData: MultipartBody, token: String): ApiResponse {
            return send(
                Request.Builder()
                    .url(url)
                    .post(bodyData)
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            )
        }

        // Method to send request
        private fun send(request: Request): ApiResponse {
            val client = OkHttpClient()
            val response = client.newCall(request).execute()
            return ApiResponse(
                response.code,
                response.body?.string(),
                response.message
            )
        }
    }
}