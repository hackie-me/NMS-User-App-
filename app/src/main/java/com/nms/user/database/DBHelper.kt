package com.nms.user.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.nms.user.utils.Helper

class DBHelper() {
    companion object{
        // Global variables
        const val DBName: String = "NMSAdminApp"

        // Function to get Database
        fun getDB(context: Context): SQLiteDatabase {
            val db = context.openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS cart (id VARCHAR, productId VARCHAR)")
            return db
        }
    }
}