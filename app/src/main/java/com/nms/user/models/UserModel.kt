package com.nms.user.models

data class UserModel(
    val id: Int = 0,
    val full_name: String = "",
    val address : String = "",
    val city : String = "",
    val state : String = "",
    val zip : String = "",
    val phone: String = "",
    val username : String = "",
    val status : String = "",
    val email : String = "",
    val mail_hash : String = "",
    val email_verified_at : String = "",
    val password: String = "",
    val confirm_password : String = "",
    val social_id : String = "",
    val image : String = "",
    val token: String = ""
)
