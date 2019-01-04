package com.example.miquelbarnadatinto.socialwall.model

import com.google.gson.annotations.SerializedName

data class TWGames (
    var id: String? = null,
    var name: String? = null,
    @SerializedName("box_art_url") var boxArt: String? =null




    ) {
    fun getImageUrl(): String? {
        return boxArt?.replace("{width}x{height}", "500x500")
    }

}

data class TWGamesResponse(
    var data: ArrayList<TWGames>? = null
)
/*"data": [
{
    "id": "493057",
    "name": "PLAYERUNKNOWN'S BATTLEGROUNDS",
    "box_art_url": "https://static-cdn.jtvnw.net/ttv-boxart/PLAYERUNKNOWN%27S%20BATTLEGROUNDS-{width}x{height}.jpg"
},
...*/