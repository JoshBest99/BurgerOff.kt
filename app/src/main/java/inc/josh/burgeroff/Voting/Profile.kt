package inc.josh.burgeroff.Voting

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import inc.josh.burgeroff.DataModels.Ratings
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.LoggingIn.LogIn
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.user_item.view.*
import java.util.*

class Profile : AppCompatActivity(){

    private lateinit var user : User
    private var progressDialog : ProgressDialog? = null
    private val TAG = "Profile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        progressDialog = ProgressDialog(this)

        getUser()

        tv_back.setOnClickListener {
            onBackPressed()
        }

        iv_edit.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1337)
        }

        btn_logout.setOnClickListener {
            var sharedPreferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putString("email", "")
            editor.putString("password","")
            editor.apply()
            startActivity(Intent(this@Profile, LogIn::class.java))

        }
    }

    private fun getUser(){
        runOnUiThread {
            progressDialog?.setMessage("Getting data...")
            progressDialog?.show()
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users").child("/${FirebaseAuth.getInstance().uid}")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("VOTINGPAGE", p0.value.toString())
                user = p0.getValue(User::class.java)!!

                if(!user.burgerImageUrl.equals("")){
                    Glide.with(this@Profile).load(user.burgerImageUrl).into(iv_profile)
                } else {
                    iv_profile.setImageDrawable(resources.getDrawable(R.drawable.profileplaceholder))
                }
            }
        })

        runOnUiThread {
            progressDialog!!.dismiss()
        }
    }

    private fun uploadBurgerImageToFirebase(selectedPhotoUri: Uri){
        runOnUiThread {
            progressDialog?.setMessage("Uploading Image...")
            progressDialog?.show()
        }
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Image Uploaded ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {

                    Log.d(TAG,"Saving url")
                    saveUrlToDatabase(it.toString())
                }
            }
            .addOnFailureListener {

                runOnUiThread {
                    progressDialog?.cancel()
                }

                Log.d(TAG, "Image failed to upload, ${it.message.toString()}")
            }
    }

    private fun saveUrlToDatabase(profileImageUrl: String){
        val ref = FirebaseDatabase.getInstance().getReference("/users/${user.uid}/burgerImageUrl")

        ref.setValue(profileImageUrl)
            .addOnFailureListener {
                Log.d(TAG, "URL upload Failed, ${it.message.toString()}")
            }

        runOnUiThread {
            progressDialog?.cancel()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1337 && resultCode == Activity.RESULT_OK && data != null){
            uploadBurgerImageToFirebase(data.data)
            Glide.with(this).load(data.data).into(iv_profile)

        }
    }

}