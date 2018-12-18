package com.example.miquelbarnadatinto.socialwall.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.Toast
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.miquelbarnadatinto.socialwall.COLLECTION_USERS
import com.example.miquelbarnadatinto.socialwall.R
import com.example.miquelbarnadatinto.socialwall.model.UserProfile
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Login_user.setOnClickListener {

        val emailTxt = findViewById<View>(R.id.emailInput) as EditText
        val passwordTxt = findViewById<View>(R.id.passordInput) as EditText

            var email = emailTxt.text.toString()
            var password = passwordTxt.text.toString()

            if (!email.isEmpty() && !password.isEmpty()) {
                var mAuth = FirebaseAuth.getInstance()

                Login_user.isEnabled = false;
                progressBar_Login.visibility = View.VISIBLE

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            Toast.makeText(this, "Log in Correcto, Bienvenido", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Email o Password Incorrecto", Toast.LENGTH_LONG).show()
                            Login_user.isEnabled = true;
                            progressBar_Login.visibility = View.GONE
                        }
                    }
            } else {
                Toast.makeText(this, "Rellena todos los huecos", Toast.LENGTH_LONG).show()
                Login_user.isEnabled = true;
                progressBar_Login.visibility = View.GONE
            }
        }
    }
}
