package com.example.miquelbarnadatinto.socialwall.Fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.miquelbarnadatinto.socialwall.*
import com.example.miquelbarnadatinto.socialwall.Adapters.MessagesAdapter
import com.example.miquelbarnadatinto.socialwall.Adapters.NewsAdapter
import com.example.miquelbarnadatinto.socialwall.activity.SignUpActivity
import com.example.miquelbarnadatinto.socialwall.model.NewsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        refreshData()
        //Pull to Refresh Listener

        pullNewsRefresh.setOnRefreshListener {
            refreshData()
        }
        val db = FirebaseFirestore.getInstance()

        if (FirebaseAuth.getInstance().currentUser == null){
            val signupIntent = Intent(activity, SignUpActivity::class.java)
            startActivity(signupIntent)

        }

        //Get user
        val authUser = FirebaseAuth.getInstance().currentUser!!
        db.collection(COLLECTION_NEWS).document(authUser.uid).get()
            .addOnSuccessListener { documentSnapshot ->
                val userProfile = documentSnapshot.toObject(NewsModel::class.java)

                //Get User Text
               /* var author: String? = null,
                var createdAt: Date? = null,
                var description: String? = null,
                var title: String? = null,
                var avatarUrl:String? = null*/
                val userNews = NewsModel(
                    author = userProfile?.author,
                    createdAt = Date(),
                    description = userProfile?.description,
                    title = userProfile?.title,
                    imageUrl = userProfile?.imageUrl
                )

                db.collection(COLLECTION_MESSAGES).add(userNews)
                    .addOnSuccessListener {
                        refreshData()

                    }.addOnFailureListener{
                        //TODO: Oh shit
                    }
            }

}

private fun refreshData(){
    pullNewsRefresh.isRefreshing = true;
    val db = FirebaseFirestore.getInstance()

    db.collection(COLLECTION_NEWS).orderBy(MESSAGE_KEY_DATE, Query.Direction.DESCENDING).get().addOnCompleteListener{ task->
        if (task.isSuccessful()) {

            val list = ArrayList<NewsModel>()
            task.result?.forEach{ documentSnapshot ->
                //documentSnapshot.data
                val message = documentSnapshot.toObject(NewsModel::class.java)
                list.add(message)
            }
            activity?.let {
                NewsRecyclerView.adapter = NewsAdapter(list)
                NewsRecyclerView.layoutManager = LinearLayoutManager(activity)

                //End refresh
                pullNewsRefresh.isRefreshing = false;
            }
        } else {
            //TODO: Oh shit

            //End refresh
            pullNewsRefresh.isRefreshing = false;
        }
    }
 }
}


