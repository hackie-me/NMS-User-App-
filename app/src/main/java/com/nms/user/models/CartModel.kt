package com.nms.user.models

data class CartModel(
    val id: String = "",
    val uid: String = "",
    val pid: String = "",
    val quantity: String = "",
    val price: String = "",
    var operation: String = ""
)
