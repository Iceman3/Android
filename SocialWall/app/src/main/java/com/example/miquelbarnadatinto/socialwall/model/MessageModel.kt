package com.example.miquelbarnadatinto.socialwall.model

import java.util.*

data class MessageModel (
    var text: String? = null,
    var createdAt: Date? = null,
    var username: String? = null,
    var userId: String? = null,
    var likes: Int?  = null,
    var avatarUrl:String? = null
)
