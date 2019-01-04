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
import com.example.miquelbarnadatinto.socialwall.Adapters.TwitchAdapter
import com.example.miquelbarnadatinto.socialwall.model.*
import kotlinx.android.synthetic.main.fragment_twitch.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TwitchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getApiData()
        refreshData()

        //Pull to Refresh Listener
        pullTwitchRefresh.setOnRefreshListener {
            refreshData()
          }

    }

    fun getApiData(){
        val list =  ArrayList<TWStream>()



        ApiService.service.getStreams().enqueue(object : Callback<TWStreamResponse> {
            override fun onFailure(call: Call<TWStreamResponse>, t: Throwable) {

                Log.e("Main Activity", "Ni idea de que ha pasado hulio!",t)
            }

            override fun onResponse(call: Call<TWStreamResponse>, response: Response<TWStreamResponse>) {


                response.body()?.data?.let { streams ->


                    for (stream in streams) {

                        val twitchChannel = TWStream(
                            userName = stream.userName,
                            title = stream.title,
                            viewerCount = stream.viewerCount,
                            userId = stream.userId,
                            thumbnailUrl = stream.getImageUrl()

                        )
                       // Log.i("main Activity", "${stream.getImageUrl()}")
                        list.add(twitchChannel)

                    }

                }
                activity?.let {
                    TwitchRecyclerView.adapter = TwitchAdapter(list)
                    TwitchRecyclerView.layoutManager = LinearLayoutManager(activity)
                }
            }


        })


    }




    private fun refreshData() {
        pullTwitchRefresh.isRefreshing = true;

        getApiData()


        pullTwitchRefresh.isRefreshing = false;

    }


}









