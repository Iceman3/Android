package com.example.miquelbarnadatinto.socialwall.model

import com.google.gson.annotations.SerializedName

data class TWStream (
    var id: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("user_name") var userName: String? =null,
    @SerializedName("game_id") var gameId: String? =null,
    var type: String?  = null,
    var title: String? = null,
    @SerializedName("viewer_count") var viewerCount: String? =null,
    @SerializedName("thumbnail_url") var thumbnailUrl: String? =null




    ){
    fun getImageUrl(): String? {
        return thumbnailUrl?.replace("{width}x{height}", "500x500")
    }
}

data class TWStreamResponse(
    var data: ArrayList<TWStream>? =null
)


/*"data": [
{
    "id": "26007351216",
    "user_id": "7236692",
    "user_name": "BillyBob",
    "game_id": "29307",
    "community_ids": [
    "848d95be-90b3-44a5-b143-6e373754c382",
    "fd0eab99-832a-4d7e-8cc0-04d73deb2e54",
    "ff1e77af-551d-4993-945c-f8ceaa2a2829"
    ],
    "type": "live",
    "title": "[Punday Monday] Necromancer - Dan's First Character - Maps - !build",
    "viewer_count": 5723,
    "started_at": "2017-08-14T15:45:17Z",
    "language": "en",
    "thumbnail_url": "https://static-cdn.jtvnw.net/previews-ttv/live_user_dansgaming-{width}x{height}.jpg"
},
...
],
"pagination": {
    "cursor": "eyJiIjp7Ik9mZnNldCI6MH0sImEiOnsiT2Zmc2V0Ijo0MH19"
}*/