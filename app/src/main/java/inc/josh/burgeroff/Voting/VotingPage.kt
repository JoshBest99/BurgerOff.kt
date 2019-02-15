package inc.josh.burgeroff.Voting

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.LoggingIn.SignUp
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.activity_voting_page.*

class VotingPage : AppCompatActivity() {

    var user : User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting_page)

        var gson : Gson = Gson()
        user = gson.fromJson(intent.getStringExtra("userData"), User::class.java)
        topText.text = "Voting for: ${user.username}"

        pattySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                pattyTitle.text = "Patty: $i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        tasteSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                tasteTitle.text = "Taste: $i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        looksSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                looksTitle.text = "Looks: $i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        backButton.setOnClickListener {
            startActivity(Intent(this@VotingPage, PageSelection::class.java))
        }

        submitButton.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("/users/${user.uid}").child("/ratings")
            ref.child("/appearance").setValue(looksSeekBar.progress + user.ratings!!.appearance)
            ref.child("/burgerTaste").setValue(tasteSeekBar.progress + user.ratings!!.burgerTaste)
            ref.child("/pattyTaste").setValue(pattySeekBar.progress + user.ratings!!.pattyTaste)
            ref.child("/ratedUids").setValue(user.ratings!!.ratedUids + FirebaseAuth.getInstance().uid)

            Toast.makeText(this@VotingPage, "You have voted for ${user.username}", Toast.LENGTH_SHORT)
            startActivity(Intent(this@VotingPage, PageSelection::class.java))
        }


    }

}