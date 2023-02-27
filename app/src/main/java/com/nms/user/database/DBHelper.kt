package com.nms.user.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DBHelper {
    companion object {
        // Global variables
        private const val DBName: String = "NMSAdminApp"

        // Function to get Database
        fun getDB(context: Context): SQLiteDatabase {
            val db = context.openOrCreateDatabase(DBName, Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS cart (id INTEGER PRIMARY KEY AUTOINCREMENT, productId VARCHAR, quantity INTEGER DEFAULT 1, Price INTEGER DEFAULT 0)")
            return db
        }
    }
}