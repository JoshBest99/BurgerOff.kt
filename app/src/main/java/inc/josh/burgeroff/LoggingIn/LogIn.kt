package inc.josh.burgeroff.LoggingIn

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import inc.josh.burgeroff.Voting.PageSelection
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.activity_main.*



class LogIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        continueButton.setOnClickListener {
            logIn()
        }

        signUp.setOnClickListener {
            startActivity(Intent(this@LogIn, SignUp::class.java))
        }
    }

    private fun logIn(){
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

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            runOnUiThread {
                progressDialog.cancel()
            }
            if(!it.isSuccessful) {
                Toast.makeText(this,"Unsuccessful $it", Toast.LENGTH_LONG).show()
                return@addOnCompleteListener
            }

            startActivity(Intent(this@LogIn, PageSelection::class.java))

        }
            .addOnFailureListener {
                runOnUiThread {
                    progressDialog.cancel()
                }
                Toast.makeText(this,"${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}
