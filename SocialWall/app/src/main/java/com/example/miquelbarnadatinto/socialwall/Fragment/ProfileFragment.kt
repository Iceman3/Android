package com.example.miquelbarnadatinto.socialwall.Fragment


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.miquelbarnadatinto.socialwall.COLLECTION_USERS
import com.example.miquelbarnadatinto.socialwall.R
import com.example.miquelbarnadatinto.socialwall.activity.LoginActivity
import com.example.miquelbarnadatinto.socialwall.activity.MainActivity
import com.example.miquelbarnadatinto.socialwall.activity.SignUpActivity
import com.example.miquelbarnadatinto.socialwall.model.UserProfile
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.row_message.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseAuth.getInstance().currentUser?.let{
            //We have user
            userFields.visibility = View.VISIBLE
            signupButton.visibility = View.GONE
            userLogButton.visibility = View.GONE
            LogOut.visibility = View.VISIBLE
            guess.visibility = View.GONE

            //Fill user data
            val db = FirebaseFirestore.getInstance()

                //Get user
                db.collection(COLLECTION_USERS).document(it.uid).get()
                    .addOnSuccessListener { documentSnapshot ->
                        val userProfile = documentSnapshot.toObject(UserProfile::class.java)
                        userProfile?.let{userProfile->
                            username.text = "Username: " + userProfile.username
                            userEmail.text = "Email: " + userProfile.email

                            if(userProfile.avatarUrl != null) {
                                Glide.with(this).load(userProfile.avatarUrl).into(userImage)
                            }


                            //Image change
                            userImage.setOnClickListener {
                                takePicture();
                            }

                        }

                        LogOut.setOnClickListener {
                            FirebaseAuth.getInstance().signOut()
                            val signupIntent = Intent(activity, MainActivity::class.java)
                            startActivity(signupIntent)
                            return@setOnClickListener
                        }
                    }
                    .addOnFailureListener {
                        //TODO: failure
                    }

        }?: kotlin.run{
            // No user
            signupButton.visibility = View.VISIBLE
            LogOut.visibility = View.GONE
            guess.visibility = View.VISIBLE

            signupButton.setOnClickListener {
                if (FirebaseAuth.getInstance().currentUser == null){
                    val signupIntent = Intent(activity, SignUpActivity::class.java)
                    startActivity(signupIntent)
                    return@setOnClickListener
                }
            }

            userLogButton.setOnClickListener{
                if (FirebaseAuth.getInstance().currentUser == null){
                    val signupIntent = Intent(activity, LoginActivity::class.java)
                    startActivity(signupIntent)
                    return@setOnClickListener
                }
            }

        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun takePicture() {
        activity?.let { activity ->
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(activity.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        ex.printStackTrace()

                        Log.e("ProfileFragment", "Error creating camera file")
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            activity,
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }

    }

    var mCurrentPhotoPath: String? = null

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        activity?.let {activity->
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            ).apply {
                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPhotoPath = absolutePath
            }
        }
        return null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            mCurrentPhotoPath?.let {
              userImage.setImageURI(Uri.fromFile(File(it)))

                //Upload to Store
                val file = File(it)
                val avatarStorageReference = FirebaseStorage.getInstance().getReference("images/users/${file.name}.jpg")

                val uri = Uri.fromFile(file)
                val uploadTask = avatarStorageReference.putFile(uri)

                uploadTask .addOnSuccessListener {
                    // All good!
                    //Set uri to User Profile
                    // Get Download Url
                    val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                it.printStackTrace()
                            }
                        }
                        return@Continuation avatarStorageReference.downloadUrl
                    }).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Got URL!!
                            val downloadUri = task.result
                            // TODO: Save to user profile
                            FirebaseAuth.getInstance().currentUser?.uid?.let {uid ->
                                // Actualizamos el documento del usuario
                                FirebaseFirestore.getInstance().collection(COLLECTION_USERS).document(uid).update("avatarUrl", downloadUri.toString())
                            }


                        } else {
                            // Handle failures
                            Log.w("ProfileFragment", "Error getting download url :( " + task.exception?.message)
                        }
                    }


                }
                    .addOnFailureListener {
                        // Handle unsuccessful uploads
                        it.printStackTrace()
                    }


            }
            //val imageBitmap = data?.extras?.get("data") as Bitmap
            //userImage.setImageBitmap(imageBitmap)
        }

    }




}
