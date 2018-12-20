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
import com.example.miquelbarnadatinto.socialwall.model.MessageModel
import kotlinx.android.synthetic.main.row_message.view.*


class MessagesAdapter(var list: ArrayList<MessageModel>) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    var onItemClickListener: OnItemClickListener<MessageModel>? = null

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        val message = list[position]
        viewHolder.text.text = message.text
        viewHolder.user.text = message.username

        Glide
            .with(viewHolder.userAvatar)
            .load(message.avatarUrl)
            .apply(
                RequestOptions()
                    .transform(CenterCrop())
                    .placeholder(R.drawable.ic_person)
            )
            .into(viewHolder.userAvatar)
        //viewHolder.userAvatar.setImageURI = message.avatarUrl

        // SoundButton sound
        /*viewHolder.soundButton.setOnClickListener {
            onItemClickListener?.onItemClick(list[position], position)
        }*/

    }


    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.messageText
        var user: TextView = itemView.userMessage
        var userAvatar: ImageView = itemView.userAvatar
    }

}