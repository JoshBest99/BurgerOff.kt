package inc.josh.burgeroff.Voting

import android.content.Intent
import android.media.Rating
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import inc.josh.burgeroff.DataModels.Score
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.LoggingIn.SignUp
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.activity_page_selection.*
import kotlinx.android.synthetic.main.activity_voting_page.*

class VotingPage : AppCompatActivity() {

    private var user : User = User(null, null, null, null, null)
    private lateinit var gson : Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting_page)

        gson = Gson()
        user = gson.fromJson(intent.getStringExtra("userData"), User::class.java)
        topText.text = "Voting for: ${user.username}"

        pattySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
            pattyTitle.text = "Patty Quality: $i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        tasteSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                tasteTitle.text = "Overall Burger Taste: $i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        looksSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                looksTitle.text = "Appearance: $i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        backButton.setOnClickListener {
            startActivity(Intent(this@VotingPage, PageSelection::class.java))
        }
            //TODO Dont set, just update will break if multi people doing it
        submitButton.setOnClickListener {
            getUpdatedVotes()
        }


    }

    private fun getUpdatedVotes(){
        val ref = FirebaseDatabase.getInstance().getReference("/users").child("/${user.uid}").child("/ratings")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
//                Log.d("VOTINGPAGE", p0.value.toString())
//                user.ratings = gson.fromJson(p0.value.toString(), Ratings::class.java)
            }
        })

        postVotes()
    }

    private fun postVotes(){
//        val ref = FirebaseDatabase.getInstance().getReference("/users/${user.uid}").child("/ratings")
//        ref.child("/appearance").setValue(looksSeekBar.progress + user.ratings!!.appearance!!)
//        ref.child("/burgerTaste").setValue(tasteSeekBar.progress + user.ratings!!.burgerTaste!!)
//        ref.child("/pattyTaste").setValue(pattySeekBar.progress + user.ratings!!.pattyTaste!!)
//        ref.child("/ratedUids").setValue(user.ratings!!.ratedUids + FirebaseAuth.getInstance().uid)
//
//        val currentUserRef = FirebaseDatabase.getInstance().getReference("/users/${FirebaseAuth.getInstance().uid}").child("/ratings/ratedScores/${user.username}")
//        currentUserRef.setValue(Score("${looksSeekBar.progress}/10", "${tasteSeekBar.progress}/40", "${pattySeekBar.progress}/50"))
//
//        Toast.makeText(this@VotingPage, "You have voted for ${user.username}", Toast.LENGTH_SHORT)
//        startActivity(Intent(this@VotingPage, PageSelection::class.java))
    }

}