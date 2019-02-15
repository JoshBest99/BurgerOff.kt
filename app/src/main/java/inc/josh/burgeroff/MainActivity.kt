package inc.josh.burgeroff

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        continueButton.setOnClickListener {
            logIn()
        }

        signUp.setOnClickListener {
            startActivity(Intent(this@MainActivity,SignUp::class.java))
        }
    }

    private fun logIn(){
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if(email.isEmpty()|| password.isEmpty()){
            Toast.makeText(this,"Please enter your username and password.", Toast.LENGTH_LONG).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(!it.isSuccessful) {
                Toast.makeText(this,"Unsuccessful $it", Toast.LENGTH_LONG).show()
                return@addOnCompleteListener
            }

            startActivity(Intent(this@MainActivity,PageSelection::class.java))

        }
            .addOnFailureListener {
                Toast.makeText(this,"${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}
