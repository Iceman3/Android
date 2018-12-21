package com.example.miquelbarnadatinto.socialwall.Fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.miquelbarnadatinto.socialwall.*
import com.example.miquelbarnadatinto.socialwall.Adapters.MessagesAdapter
import com.example.miquelbarnadatinto.socialwall.activity.MainActivity
import com.example.miquelbarnadatinto.socialwall.activity.SignUpActivity
import com.example.miquelbarnadatinto.socialwall.model.MessageModel
import com.example.miquelbarnadatinto.socialwall.model.UserProfile
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshData()

        //Pull to Refresh Listener
        pullToRefresh.setOnRefreshListener {
            refreshData()
        }
        val db = FirebaseFirestore.getInstance()

        sendButton.setOnClickListener {

            if (FirebaseAuth.getInstance().currentUser == null){
                val signupIntent = Intent(activity, SignUpActivity::class.java)
                startActivity(signupIntent)
                return@setOnClickListener
            }

            //Get user
            val authUser = FirebaseAuth.getInstance().currentUser!!


                    val userPreferences = context?.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)
                    val userId = userPreferences?.getString(PREF_USERID, "")
                    val username = userPreferences?.getString(PREF_USERNAME, "")
                    val userImage = userPreferences?.getString(PREF_AVIMAGE,"")


            //Get User Text
            var userText = userInput.text.toString()
                    if(userId == null || userImage == null || username == null) {
                        db.collection(COLLECTION_USERS).document(authUser.uid).get()
                            .addOnSuccessListener { documentSnapshot ->
                                val userProfile = documentSnapshot.toObject(UserProfile::class.java)
                                val userMessage = MessageModel(
                                    text = userText,
                                    username = userProfile?.username,
                                    createdAt = Date(),
                                    userId = userProfile?.userId,
                                    avatarUrl = userProfile?.avatarUrl
                                )

                            }
                    }
                    val userMessage = MessageModel(
                        text = userText,
                        username = username,
                        createdAt = Date(),
                        userId = userId,
                        avatarUrl = userImage
                    )

                    db.collection(COLLECTION_MESSAGES).add(userMessage)
                        .addOnSuccessListener {
                            refreshData()
                            userInput.text = null
                        }.addOnFailureListener{
                            //TODO: Oh shit
                        }
                }

        }


    private fun refreshData(){
        pullToRefresh.isRefreshing = true;
        val db = FirebaseFirestore.getInstance()

        db.collection(COLLECTION_MESSAGES).orderBy(MESSAGE_KEY_DATE, Query.Direction.DESCENDING).get().addOnCompleteListener{ task->
            if (task.isSuccessful()) {

                val list = ArrayList<MessageModel>()
                task.result?.forEach{ documentSnapshot ->
                    //documentSnapshot.data
                    val message = documentSnapshot.toObject(MessageModel::class.java)
                    list.add(message)
                }

                activity?.let {
                    recyclerView.adapter = MessagesAdapter(list)
                    recyclerView.layoutManager = LinearLayoutManager(activity)

                    //End refresh
                    pullToRefresh.isRefreshing = false;
                }
            } else {
                //TODO: Oh shit

                //End refresh
                pullToRefresh.isRefreshing = false;
            }
        }
    }
}
