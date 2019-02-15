package inc.josh.burgeroff

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.Matrix
import com.bumptech.glide.Glide


class SignUp : AppCompatActivity() {

    private var selectedPhotoUri: Uri? = null
    private var progressDialog : ProgressDialog? = null

    val TAG: String = "SIGNUPACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        progressDialog = ProgressDialog(this)

        continueButton.setOnClickListener {
            registerUser()
        }

        backToLogin.setOnClickListener {

            startActivity(Intent(this@SignUp,LogIn::class.java))
        }

        addPhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1337)
        }

    }

    private fun registerUser(){
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        runOnUiThread {
            progressDialog?.setMessage("Registering...")
            progressDialog?.show()
        }

        if(email.isEmpty()|| password.isEmpty()){
            runOnUiThread {
                progressDialog?.cancel()
            }
            Toast.makeText(this,"Please enter your username and password.", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(!it.isSuccessful) {

                    runOnUiThread {
                        progressDialog?.cancel()
                    }
                    Toast.makeText(this,"Unsuccessful $it", Toast.LENGTH_LONG).show()
                    return@addOnCompleteListener
                }

                Log.d(TAG,"Auth Done, uploading image")
                uploadImageToFirebase()
            }
            .addOnFailureListener {
                runOnUiThread {
                    progressDialog?.cancel()
                }
                Toast.makeText(this,"${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1337 && resultCode == Activity.RESULT_OK && data != null){
            selectedPhotoUri = data.data
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
//            circleImage.setImageBitmap(bitmap)
            addPhotoButton.alpha = 0f
            Glide.with(this).load(selectedPhotoUri).into(circleImage)

        }
    }

    private fun uploadImageToFirebase(){
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Image Uploaded ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {

                    Log.d(TAG,"Saving user")
                    saveUserToDatabase(it.toString())
                }
            }
            .addOnFailureListener {

                runOnUiThread {
                    progressDialog?.cancel()
                }

                Log.d(TAG, "Image failed to upload, ${it.message.toString()}")
            }
    }

    private fun saveUserToDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, nameEditText.text.toString(), profileImageUrl)

        runOnUiThread {
            progressDialog?.cancel()
        }

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG,"User Saved")
                startActivity(Intent(this@SignUp,LogIn::class.java))
            }
            .addOnFailureListener {
                Log.d(TAG, "User Failed, ${it.message.toString()}")
            }
    }
}

class User(var uid: String, var username: String, var profileImageUrl: String)
