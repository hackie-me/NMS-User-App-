package com.nms.user.models

data class OrderModel(
    val id: String = "",
    val address: String = "",
    val pid: String = "",
    val note: String = "",
    val quantity: String = "",
    val pdf: String = "",
    val total: String = "",
    val status: String = ""
)
