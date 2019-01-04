package com.example.miquelbarnadatinto.socialwall.Adapters


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.miquelbarnadatinto.socialwall.OnItemClickListener
import com.example.miquelbarnadatinto.socialwall.R
import com.example.miquelbarnadatinto.socialwall.model.TWGames
import kotlinx.android.synthetic.main.row_twitch.view.*
import kotlinx.android.synthetic.main.row_twitchgames.view.*
import java.util.ArrayList


class TwitchGamesAdapter(var list: ArrayList<TWGames>) : RecyclerView.Adapter<TwitchGamesAdapter.MessageViewHolder>() {

    var onItemClickListener: OnItemClickListener<TWGames>? = null

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_twitchgames, parent, false)

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        val message = list[position]

        viewHolder.name.text = message.name

        Glide
            .with(viewHolder.twitchImage)
            .load(message.getImageUrl())
            .apply(
                RequestOptions()
                    .transform(CenterCrop())
                    .placeholder(R.drawable.ic_person)
            )
            .into(viewHolder.twitchImage)

        //viewHolder.userAvatar.setImageURI = message.avatarUrl

        // SoundButton sound
        /*viewHolder.soundButton.setOnClickListener {
            onItemClickListener?.onItemClick(list[position], position)
        }*/

    }



    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView =  itemView.TwitchGameName
        var twitchImage: ImageView = itemView.twitchGameImage


       // var userAvatar: ImageView = itemView.newsImage
    }

}