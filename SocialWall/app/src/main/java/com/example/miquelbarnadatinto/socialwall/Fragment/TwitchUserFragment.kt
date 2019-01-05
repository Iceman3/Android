package com.example.miquelbarnadatinto.socialwall.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sergisanchezsolares.twitchcompanion.network.ApiService
import java.util.*
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.miquelbarnadatinto.socialwall.*
import com.example.miquelbarnadatinto.socialwall.Adapters.TwitchGamesAdapter
import com.example.miquelbarnadatinto.socialwall.model.*
import com.example.sergisanchezsolares.twitchcompanion.network.getUserID
import com.example.sergisanchezsolares.twitchcompanion.network.getUserName
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_twitch.*
import kotlinx.android.synthetic.main.fragment_twitchgames.*
import kotlinx.android.synthetic.main.fragment_twitchuser.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TwitchUserFragment : Fragment() {

    var twitchUserName: String?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitchuser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        userTwitchFields.visibility = View.GONE


        sendSearchButton.setOnClickListener {
            twitchUserName = userSearchInput.text.toString()
            if(twitchUserName != null) {
                getUserID(twitchUserName.toString())
                getApiUserData()
                userSearchInput.text = null
                userTwitchFields.visibility = View.VISIBLE
            }

        }


    }


   fun getApiUserData(){
        ApiService.service.getUsers().enqueue(object : Callback<TWUserResponse> {

            override fun onFailure(call: Call<TWUserResponse>, t: Throwable) {

                Log.e("Main Activity", "Ni idea de que ha pasado hulio!",t)
            }

            override fun onResponse(call: Call<TWUserResponse>, response: Response<TWUserResponse>) {

                Log.i("MainActivity", "${response.body()?.data?.size}")
                response.body()?.data?.let { streams ->

                    val list = ArrayList<TWUsers>()

                    for (stream in streams) {

                        val twitchUser = TWUsers(
                            id= stream.id,
                            login = stream.login,
                            userName = stream.userName,
                            description = stream.description,
                            profileImg = stream.getImageUrl()


                        )
                        list.add(twitchUser)
                        if(twitchUser.userName != null){
                            twitchUsername.text = twitchUser.userName
                        }
                        else{
                            twitchUsername.text = "none"
                        }

                        if(twitchUser.description != null) {
                            twitchDescription.text = twitchUser.description
                        }
                        else{
                            twitchDescription.text = "none"
                        }

                        Glide
                            .with(userTwitchImage)
                            .load(twitchUser.getImageUrl())
                            .apply(
                                RequestOptions()
                                    .transform(CenterCrop())
                                    .placeholder(R.drawable.ic_person)
                            )
                            .into(userTwitchImage)




                    }
                }
            }


        })
    }

   /* fun getApiUserIDData(){
        ApiService.service.getUserID().enqueue(object : Callback<TWUserIDResponse> {

            override fun onFailure(call: Call<TWUserIDResponse>, t: Throwable) {

                Log.e("Main Activity", "Ni idea de que ha pasado hulio!",t)
            }

            override fun onResponse(call: Call<TWUserIDResponse>, response: Response<TWUserIDResponse>) {

                //Log.i("MainActivity", "${response.body()?.data?.size}")
                response.body()?.data?.let { streams ->

                    val list = ArrayList<TWUsersID>()

                    for (stream in streams) {

                        val twitchUser = TWUsersID(
                            id= stream.id


                        )
                        list.add(twitchUser)
                        twitchUserID = twitchUser.id

                        getUserID(twitchUserID.toString())
                        Log.i("MainActivity", "Stream With title ${stream.id}")

                    }
                }
            }


        })
    }*/

}









