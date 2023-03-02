package com.nms.user.service

import android.content.Context
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nms.user.utils.Helper

class Authentication(
    private val context: Context
) {
    companion object {

        // Function to check if user is logged in
        fun isLoggedIn(context: Context): Boolean {
            return tokenExists(context)
        }

        // Function to Store Token
        fun storeToken(context: Context, token: String) {
            val sharedPref = context.getSharedPreferences(Helper.prefName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("token", token)
                commit()
            }
        }

        // Function to Check if Token Exists
        private fun tokenExists(context: Context): Boolean {
            val sharedPref = context.getSharedPreferences(Helper.prefName, Context.MODE_PRIVATE)
            return sharedPref.contains("token")
        }

        // Function to Get Token
        fun getToken(context: Context): String? {
            val sharedPref = context.getSharedPreferences(Helper.prefName, Context.MODE_PRIVATE)
            return sharedPref.getString("token", null)
        }

        // Function to Clear Token
        fun clearToken(context: Context) {
            val sharedPref = context.getSharedPreferences(Helper.prefName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                remove("token")
                commit()
            }
        }

        // Function to Decode JWT Token
        fun decodeJWTToken(token: String): String {
            val split = token.split(".")
            val payload = split[1]
            val decoded = Base64.decode(payload, Base64.DEFAULT)
            return String(decoded)
        }

        // Function to fetch user id from JWT Token
        fun getDataFromToken(context: Context, value: String): String? {
            return Gson().fromJson(
                decodeJWTToken(
                    getToken(context)!!
                ), JsonObject::class.java
            ).getAsJsonObject("data")?.get(value)?.asString
        }

        // FetchJson data from response
        fun fetchTokenFromJsonData(response: String): String? {
            val element = Gson().fromJson(response, JsonObject::class.java)
            return element.get("token").asString
        }

    }
}