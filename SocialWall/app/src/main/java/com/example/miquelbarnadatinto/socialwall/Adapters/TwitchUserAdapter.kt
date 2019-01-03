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
import com.example.miquelbarnadatinto.socialwall.R.id.twitchImage
import com.example.miquelbarnadatinto.socialwall.model.NewsModel
import com.example.miquelbarnadatinto.socialwall.model.TWStream
import com.example.miquelbarnadatinto.socialwall.model.TWStreamResponse
import com.example.miquelbarnadatinto.socialwall.model.TWUser
import kotlinx.android.synthetic.main.row_news.view.*
import kotlinx.android.synthetic.main.row_twitch.view.*
import java.util.ArrayList


class TwitchUserAdapter(var list: ArrayList<TWUser>) : RecyclerView.Adapter<TwitchUserAdapter.MessageViewHolder>() {

    var onItemClickListener: OnItemClickListener<TWUser>? = null

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_twitch, parent, false)

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        val message = list[position]


        Glide
            .with(viewHolder.twitchImage)
            .load(message.profileImage)
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


        var twitchImage: ImageView = itemView.twitchImage
        //var viewCount: TextView =  itemView.userCountTwitch

       // var userAvatar: ImageView = itemView.newsImage
    }

}