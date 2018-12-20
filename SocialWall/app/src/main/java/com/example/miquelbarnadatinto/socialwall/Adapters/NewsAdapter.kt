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
import com.example.miquelbarnadatinto.socialwall.model.MessageModel
import com.example.miquelbarnadatinto.socialwall.model.NewsModel
import kotlinx.android.synthetic.main.row_message.view.*
import kotlinx.android.synthetic.main.row_news.view.*


class NewsAdapter(var list: ArrayList<NewsModel>) : RecyclerView.Adapter<NewsAdapter.MessageViewHolder>() {

    var onItemClickListener: OnItemClickListener<NewsModel>? = null

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        val message = list[position]
        viewHolder.user.text = message.description
        viewHolder.text.text = message.title

        Glide
            .with(viewHolder.userAvatar)
            .load(message.imageUrl)
            .into(viewHolder.userAvatar)

        //viewHolder.userAvatar.setImageURI = message.avatarUrl

        // SoundButton sound
        /*viewHolder.soundButton.setOnClickListener {
            onItemClickListener?.onItemClick(list[position], position)
        }*/

    }


    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var user: TextView = itemView.descriptionNews
        var text: TextView = itemView.titleNews
        var userAvatar: ImageView = itemView.newsImage
    }

}