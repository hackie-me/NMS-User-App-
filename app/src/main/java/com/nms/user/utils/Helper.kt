package com.nms.user.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object {
        // Global variables
        const val prefName: String = "NMSAdminApp"

        // Function to Show Toast
        fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
            Toast.makeText(context, message, duration).show()
        }

        // Function to show snack-bar
        fun showSnackBar(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }

        // Function to store shared preference
        fun storeSharedPreference(
            context: Context,
            key: String,
            value: String,
            prefName: String = Helper.prefName
        ) {
            val sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(key, value)
                commit()
            }
        }

        // Function to fetch shared preference
        fun fetchSharedPreference(
            context: Context,
            key: String,
            prefName: String = Helper.prefName
        ): String {
            val sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return sharedPref.getString(key, "")!!
        }

        // Function to get All Keys from Shared Preference as Array
        fun getAllKeysFromSharedPreference(
            context: Context,
            prefName: String = Helper.prefName
        ): Array<String> {
            val sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return sharedPref.all.keys.toTypedArray()
        }

        // Function to remove shared preference
        fun removeSharedPreference(
            context: Context,
            key: String,
            prefName: String = Helper.prefName
        ) {
            val sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                remove(key)
                commit()
            }
        }

        // Function to check if the shared preference has the key
        fun hasSharedPreference(
            context: Context,
            key: String,
            prefName: String = Helper.prefName
        ): Boolean {
            val sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return sharedPref.contains(key)
        }

        // Function to show alert dialog
        fun showAlertDialog(
            context: Context,
            title: String,
            message: String,
            s: String,
            s1: String,
            function: () -> Unit,
            function1: () -> Unit
        ) {
            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(s) { _, _ ->
                function()
            }
            builder.setNegativeButton(s1) { _, _ ->
                function1()
            }
            builder.show()
        }

        // Function to convert image to base64
        fun convertImageToBase64(image: ImageView): String {
            val drawable = image.drawable
            val bitmap = (drawable as BitmapDrawable).bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP)
        }

        // Function to show Confirmation Dialog
        fun showConfirmationDialog(
            context: Context,
            title: String,
            message: String,
            yes: String,
            no: String,
            function: () -> Unit,
            function1: () -> Unit
        ) {
            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(yes) { _, _ ->
                function()
            }
            builder.setNegativeButton(no) { _, _ ->
                function1()
            }
            builder.show()
        }

        // Function to show snack-bar with action
        fun showSnackBarWithAction(
            view: View,
            message: String,
            action: String,
            function: () -> Unit
        ) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction(action) {
                function()
            }.show()
        }

        // Function to toggle progress Dialog
        fun toggleProgressBar(progressDialog: android.widget.ProgressBar, show: Boolean) {
            if (show) {
                progressDialog.visibility = View.VISIBLE
            } else {
                progressDialog.visibility = View.GONE
            }
        }

        // Function to Generate Random String
        fun generateRandomString(i: Int): String {
            val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            val sb = StringBuilder()
            for (j in 0 until i) {
                val index = (Math.random() * chars.length).toInt()
                sb.append(chars[index])
            }
            return sb.toString()
        }

        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        fun formatDate(inputDate: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM d, yyyy 'at' hh:mm a", Locale.getDefault())
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date!!)
        }
    }
}