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
import com.example.miquelbarnadatinto.socialwall.*
import com.example.miquelbarnadatinto.socialwall.Adapters.TwitchGamesAdapter
import com.example.miquelbarnadatinto.socialwall.model.*
import kotlinx.android.synthetic.main.fragment_twitch.*
import kotlinx.android.synthetic.main.fragment_twitchgames.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TwitchGamesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitchgames, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        getApiUserData()
        refreshData()

        //Pull to Refresh Listener
        pullTwitchGamesRefresh.setOnRefreshListener {
            refreshData()
          }

    }


   fun getApiUserData(){
        ApiService.service.getGames().enqueue(object : Callback<TWGamesResponse> {

            override fun onFailure(call: Call<TWGamesResponse>, t: Throwable) {

                Log.e("Main Activity", "Ni idea de que ha pasado hulio!",t)
            }

            override fun onResponse(call: Call<TWGamesResponse>, response: Response<TWGamesResponse>) {


                response.body()?.data?.let { streams ->

                    val list = ArrayList<TWGames>()

                    for (stream in streams) {

                        val twitchUser = TWGames(
                            name= stream.name,
                            boxArt = stream.getImageUrl()

                        )
                        list.add(twitchUser)

                    }
                    activity?.let {
                        TwitchGamesRecyclerView.adapter = TwitchGamesAdapter(list)
                        TwitchGamesRecyclerView.layoutManager = LinearLayoutManager(activity)
                    }
                }
            }


        })
    }
    private fun refreshData() {
        pullTwitchGamesRefresh.isRefreshing = true;


        getApiUserData()

        pullTwitchGamesRefresh.isRefreshing = false;

    }


}









