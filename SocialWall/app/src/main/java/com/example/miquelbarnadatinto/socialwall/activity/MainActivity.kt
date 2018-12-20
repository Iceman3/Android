package com.example.miquelbarnadatinto.socialwall.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.example.miquelbarnadatinto.socialwall.Fragment.HomeFragment
import com.example.miquelbarnadatinto.socialwall.Fragment.NewsFragment
import com.example.miquelbarnadatinto.socialwall.Fragment.ProfileFragment
import com.example.miquelbarnadatinto.socialwall.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabs.setOnNavigationItemSelectedListener { tab ->

            lateinit var fragment: Fragment

            when(tab.itemId){
                R.id.tab_home ->{
                    fragment = HomeFragment()
                }
                R.id.tab_news ->{
                    fragment = NewsFragment()
                }
                R.id.tab_profile ->{
                    fragment = ProfileFragment()
                }
                else ->{
                    Toast.makeText(this, "Oh!, no deberia llegar aquí nunca. ERROR 404", Toast.LENGTH_LONG).show()
                    Log.w("MainActivity","Oh!, no deberia llegar aquí nunca. ERROR 404")
                    fragment = HomeFragment()
                }
            }

            val fragmentManager = supportFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer,fragment )
            fragmentTransaction.commit()

            return@setOnNavigationItemSelectedListener true
        }
        tabs.selectedItemId = R.id.tab_home
    }
}
