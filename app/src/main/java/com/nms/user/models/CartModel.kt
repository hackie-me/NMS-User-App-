package com.nms.user.models

data class CartModel(
    val id: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val productImage: String = "",
    var productPrice: Int = 0,
    val productDiscount: String = "",
    var productQuantity: Int = 1
)
