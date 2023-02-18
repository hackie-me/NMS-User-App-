package com.nms.user.models

data class ReviewModel(
    val id: String = "",
    val uid: String = "",
    val pid: String = "",
    val name: String = "",
    val msg : String = "",
    val rating: String = "",
    val created_at: String = "",
    val updated_at: String = "",
)
