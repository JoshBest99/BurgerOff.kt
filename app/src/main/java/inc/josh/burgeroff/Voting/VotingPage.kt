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
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.LoggingIn.SignUp
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.activity_page_selection.*
import kotlinx.android.synthetic.main.activity_voting_page.*

class VotingPage : AppCompatActivity() {

    private lateinit var team : Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting_page)
        team = intent.getSerializableExtra("team") as Team
        topText.text = "Voting for: ${team.name}"

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
        val ref = FirebaseDatabase.getInstance().getReference("/teams/${team.uid}/score")

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("VOTINGPAGE", p0.value.toString())
                team.score = Gson().fromJson(p0.value.toString(), Score::class.java)
            }
        })

        postVotes()
    }

    private fun postVotes(){
        val ref = FirebaseDatabase.getInstance().getReference("/teams/${team.uid}")
        ref.child("/score/appearance").setValue((looksSeekBar.progress + team.score!!.appearance!!.toInt()).toString())
        ref.child("/score/burgerTaste").setValue((tasteSeekBar.progress + team.score!!.burgerTaste!!.toInt()).toString())
        ref.child("/score/pattyTaste").setValue((pattySeekBar.progress + team.score!!.pattyTaste!!.toInt()).toString())
        ref.child("/voteesUids").setValue(team.voteesUids + FirebaseAuth.getInstance().uid)

        val currentUserRef = FirebaseDatabase.getInstance().getReference("/users/${FirebaseAuth.getInstance().uid}").child("/ratings/ratedScores/${team.name}")
        currentUserRef.setValue(Score("${looksSeekBar.progress}/10", "${tasteSeekBar.progress}/40", "${pattySeekBar.progress}/50"))

        incrementVotesMade()

        Toast.makeText(this@VotingPage, "You have voted for ${team.name}", Toast.LENGTH_SHORT)
        startActivity(Intent(this@VotingPage, PageSelection::class.java))
    }

    private fun incrementVotesMade(){
        val votesRef = FirebaseDatabase.getInstance().getReference("/votesmade")
        var votes: Int

        votesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                votes = p0.getValue(Int::class.java)!!
                votesRef.setValue(votes + 1)

            }
        })
    }

}