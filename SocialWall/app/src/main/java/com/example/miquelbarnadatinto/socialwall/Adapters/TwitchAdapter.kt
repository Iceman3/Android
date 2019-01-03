package com.example.miquelbarnadatinto.socialwall.Adapters


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.miquelbarnadatinto.socialwall.OnItemClickListener
import com.example.miquelbarnadatinto.socialwall.R
import com.example.miquelbarnadatinto.socialwall.model.TWStream
import com.example.miquelbarnadatinto.socialwall.model.TWUser
import kotlinx.android.synthetic.main.row_twitch.view.*
import java.util.ArrayList


class TwitchAdapter(var list: ArrayList<TWStream>) : RecyclerView.Adapter<TwitchAdapter.MessageViewHolder>() {

    var onItemClickListener: OnItemClickListener<TWStream>? = null

    override fun getItemCount(): Int {
        return list.count()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_twitch, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        val message = list[position]
        viewHolder.title.text = message.title
        viewHolder.userName.text = message.userName
        viewHolder.viewCount.text = message.viewerCount+ " viewers"

       /* val message2 = list2[position]
        Glide
            .with(viewHolder.twitchImage)
            .load(message2.profileImage)
            .into(viewHolder.twitchImage)*/

    //    viewHolder.userAvatar.setImageURI = message.avatarUrl

        // SoundButton sound
        /*viewHolder.soundButton.setOnClickListener {
            onItemClickListener?.onItemClick(list[position], position)
        }*/

    }



    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var title: TextView = itemView.titleTwitch
        var userName: TextView =  itemView.userTwitch
        var viewCount: TextView = itemView.userCountTwitch
      //  var twitchImage: ImageView = itemView.twitchImage

        // var userAvatar: ImageView = itemView.newsImage
    }

}