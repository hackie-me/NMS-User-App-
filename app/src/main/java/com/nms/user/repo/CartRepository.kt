package com.nms.user.repo

import android.content.Context
import com.nms.user.database.DBHelper
import com.nms.user.utils.Helper

class CartRepository() {
    companion object{
        // Function to add product to cart
        fun addToCart(context: Context, productId: String): Boolean {
            // Open Database
            val db = DBHelper.getDB(context)
            // Generate id
            val id = Helper.generateRandomString(5)
            // Insert product to cart table
            db.execSQL("INSERT INTO cart VALUES ('$id', '$productId')")
            return true
        }

        // Function to remove product from cart
        fun removeFromCart(context: Context, productId: String): Boolean {
            // Open Database
            val db = DBHelper.getDB(context)
            // Delete product from cart table
            db.execSQL("DELETE FROM cart WHERE productId = '$productId'")
            return true
        }

        // Function to get all products from cart
        fun getAllProductsFromCart(context: Context): ArrayList<String> {
            // Open Database
            val db = DBHelper.getDB(context)
            // Get all products from cart table
            val cursor = db.rawQuery("SELECT * FROM cart", null)
            // Create array list to store products
            val products = ArrayList<String>()
            // Check if cursor has data
            if (cursor.moveToFirst()) {
                // Loop through cursor
                do {
                    // Add product to array list
                    products.add(cursor.getString(1))
                } while (cursor.moveToNext())
            }
            // Close cursor
            cursor.close()
            // Return products
            return products
        }

        // Function to check if product is in cart
        fun isProductInCart(context: Context, productId: String): Boolean {
            // Open Database
            val db = DBHelper.getDB(context)
            // Get all products from cart table
            val cursor = db.rawQuery("SELECT * FROM cart WHERE productId = '$productId'", null)
            // Check if cursor has data
            if (cursor.moveToFirst()) {
                // Close cursor
                cursor.close()
                // Return true
                return true
            }
            // Close cursor
            cursor.close()
            // Return false
            return false
        }

        // Function to clear cart
        fun clearCart(context: Context): Boolean {
            // Open Database
            val db = DBHelper.getDB(context)
            // Delete all products from cart table
            db.execSQL("DELETE FROM cart")
            return true
        }

        // Function to get cart count
        fun getCartCount(context: Context): Int {
            // Open Database
            val db = DBHelper.getDB(context)
            // Get all products from cart table
            val cursor = db.rawQuery("SELECT * FROM cart", null)
            // Get count
            val count = cursor.count
            // Close cursor
            cursor.close()
            // Return count
            return count
        }
    }
}