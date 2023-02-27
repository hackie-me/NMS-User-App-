package com.nms.user.models

data class OrderModel(
    val _id: String,
    val address: AddressModel,
    val cart: ArrayList<CartModel>,
    val date: String,
    val orderTotal: Int,
    val paymentMethod: String,
    val shippingPrice: Int,
    val status: String,
    val user: String
)
