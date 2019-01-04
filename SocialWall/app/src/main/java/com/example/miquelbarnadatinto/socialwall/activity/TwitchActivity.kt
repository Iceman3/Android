package com.example.miquelbarnadatinto.socialwall.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.*
import com.example.miquelbarnadatinto.socialwall.Fragment.*
import com.google.android.gms.ads.*
import com.example.miquelbarnadatinto.socialwall.R
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.google.android.gms.ads.reward.RewardedVideoAd
import kotlinx.android.synthetic.main.activity_twitch.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class TwitchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitch)



        twitchTabs.setOnNavigationItemSelectedListener { tab ->

            lateinit var fragment: Fragment

            when (tab.itemId) {
                R.id.tab_topStreamers -> {
                    fragment = TwitchFragment()
                }
                R.id.tab_topGames -> {
                    fragment = TwitchGamesFragment()

                }
                R.id.tab_streamUser -> {
                    fragment = TwitchFragment()

                }
                else -> {
                    Toast.makeText(this, "Oh!, no deberia llegar aquí nunca. ERROR 404", Toast.LENGTH_LONG).show()
                    Log.w("MainActivity", "Oh!, no deberia llegar aquí nunca. ERROR 404")
                    fragment = TwitchFragment()
                }
            }

            val fragmentManager = supportFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentTwitchContainer, fragment)
            fragmentTransaction.commit()

            return@setOnNavigationItemSelectedListener true
        }
        twitchTabs.selectedItemId = R.id.tab_topStreamers
    }
}