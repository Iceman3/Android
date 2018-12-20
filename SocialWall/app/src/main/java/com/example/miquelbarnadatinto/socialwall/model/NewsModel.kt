package com.example.miquelbarnadatinto.socialwall.model

import java.io.FileDescriptor
import java.util.*

data class NewsModel (
    var author: String? = null,
    var createdAt: Date? = null,
    var description: String? = null,
    var title: String? = null,
    var imageUrl:String? = null
)
