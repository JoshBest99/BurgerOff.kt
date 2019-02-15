package inc.josh.burgeroff

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        continueButton.setOnClickListener {
            registerUser()
        }

        backToLogin.setOnClickListener {

            startActivity(Intent(this@SignUp,MainActivity::class.java))
        }

    }

    private fun registerUser(){
        val progressDialog = ProgressDialog(this)
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        runOnUiThread {
            progressDialog.setMessage("Registering...")
            progressDialog.show()
        }

        if(email.isEmpty()|| password.isEmpty()){
            runOnUiThread {
                progressDialog.cancel()
            }
            Toast.makeText(this,"Please enter your username and password.", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                runOnUiThread {
                    progressDialog.cancel()
                }
                if(!it.isSuccessful) {
                    Toast.makeText(this,"Unsuccessful $it", Toast.LENGTH_LONG).show()
                    return@addOnCompleteListener
                }

                Toast.makeText(this,"Success!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@SignUp,MainActivity::class.java))
            }
            .addOnFailureListener {
                runOnUiThread {
                    progressDialog.cancel()
                }
                Toast.makeText(this,"${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}