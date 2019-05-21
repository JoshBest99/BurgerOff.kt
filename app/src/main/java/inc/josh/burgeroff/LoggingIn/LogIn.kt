package inc.josh.burgeroff.LoggingIn

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import inc.josh.burgeroff.Voting.PageSelection
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.activity_main.*



class LogIn : AppCompatActivity() {

    private var email : String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sharedPreferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        email = sharedPreferences.getString("email", "")
        password = sharedPreferences.getString("password", "")

        btn_continue.setOnClickListener {
            logIn(sharedPreferences.edit())
        }

        tv_signup.setOnClickListener {
            startActivity(Intent(this@LogIn, SignUp::class.java))
        }

        if(!email.equals("") && !password.equals("")){
            logIn(sharedPreferences.edit())
        }
    }

    private fun logIn(editor: SharedPreferences.Editor){
        val progressDialog = ProgressDialog(this)

        if(email.equals("") || password.equals("")){
            email = et_email.text.toString()
            password = et_password.text.toString()

        }

        runOnUiThread {
            progressDialog.setMessage("Logging in...")
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

            editor.putString("email", email)
            editor.putString("password", password)
            editor.apply()
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
