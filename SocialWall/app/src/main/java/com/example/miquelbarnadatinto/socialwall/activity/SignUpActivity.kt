package com.example.miquelbarnadatinto.socialwall.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.Toast
import android.util.Log
import android.view.View
import com.example.miquelbarnadatinto.socialwall.COLLECTION_USERS
import com.example.miquelbarnadatinto.socialwall.R
import com.example.miquelbarnadatinto.socialwall.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        submitButton.setOnClickListener(){
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passordInput.text.toString()

            //Validation
            if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                submitButton.isEnabled = false
                progressBar.visibility = View.VISIBLE
                //Sign Up
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUpActivity", "createUserWithEmail:success")
                            val userAuth = FirebaseAuth.getInstance().currentUser
                            userAuth?.let { userAuth->
                                //Create user pojo
                                val userProfile = UserProfile(
                                    username = username,
                                    userId = userAuth.uid,
                                    email = email
                                )
                                //"users"
                                val db = FirebaseFirestore.getInstance()
                                db.collection(COLLECTION_USERS).document(userAuth.uid).set(userProfile)
                                    .addOnCompleteListener{task ->
                                        if (task.isSuccessful){
                                            //ALL OK
                                            Toast.makeText(this, "Usuario Creado", Toast.LENGTH_LONG).show()
                                            finish()
                                        }else{
                                            //Ooops
                                            submitButton.isEnabled = true
                                            Toast.makeText(this, "No se ha podido crear el usuario, Intentalo de nuevo",Toast.LENGTH_SHORT).show()
                                            progressBar.visibility = View.INVISIBLE
                                        }
                                    }
                                userAuth.uid
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                this@SignUpActivity, "Ha fallado la Autentificación",
                                Toast.LENGTH_SHORT
                            ).show()
                            submitButton.isEnabled = true
                            progressBar.visibility = View.INVISIBLE
                        }

                        // ...
                    }

            }
        }

        goToLogin.setOnClickListener(){
            val signupIntent = Intent(this, LoginActivity::class.java)
            startActivity(signupIntent)
            return@setOnClickListener
        }
    }
}
