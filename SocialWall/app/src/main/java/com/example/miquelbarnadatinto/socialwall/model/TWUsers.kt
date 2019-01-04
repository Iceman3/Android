package com.example.miquelbarnadatinto.socialwall.model

import com.google.gson.annotations.SerializedName

data class TWUsers (
    var id: String? = null,
    var login: String? = null,
    @SerializedName("display_name") var userName: String? =null,
    var description: String? =null,
    @SerializedName("profile_image_url") var profileImg: String? =null




    ){
    fun getImageUrl(): String? {
        return profileImg?.replace("{width}x{height}", "500x500")
    }
}

data class TWUserResponse(
    var data: ArrayList<TWUsers>? =null
)


/*
"data": [{
    "id": "44322889",
    "login": "dallas",
    "display_name": "dallas",
    "type": "staff",
    "broadcaster_type": "",
    "description": "Just a gamer playing games and chatting. :)",
    "profile_image_url": "https://static-cdn.jtvnw.net/jtv_user_pictures/dallas-profile_image-1a2c906ee2c35f12-300x300.png",
    "offline_image_url": "https://static-cdn.jtvnw.net/jtv_user_pictures/dallas-channel_offline_image-1a2c906ee2c35f12-1920x1080.png",
    "view_count": 191836881,
    "email": "login@provider.com"
}]
*/