package com.example.miquelbarnadatinto.socialwall.model

data class UserProfile(
    var avatarUrl: String? = null,
    var username: String? = null,
    var userId: String? = null,
    var email:String? = null,
    var messageCount: Int? = null
)