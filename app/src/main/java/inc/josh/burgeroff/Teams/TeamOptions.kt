package inc.josh.burgeroff.Teams

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.PageSelection
import kotlinx.android.synthetic.main.activity_team_options.*

class TeamOptions: AppCompatActivity(){

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_options)
        getCurrentUser()

        btn_cooking_yes.setOnClickListener {
            changeTeamChoiceUI()
        }

        btn_cooking_no.setOnClickListener {
            joinNoTeam(user)
        }

        btn_create_team.setOnClickListener {
            val createTeamDialog = CreateTeamDialog(this, user)
            createTeamDialog.show()
        }

        btn_join_team.setOnClickListener {
            val joinTeamDialog = JoinTeamDialog(this, user)
            joinTeamDialog.show()
        }

    }

    private fun changeTeamChoiceUI(){
        btn_create_team.visibility = View.VISIBLE
        btn_join_team.visibility = View.VISIBLE

        btn_cooking_yes.visibility = View.GONE
        btn_cooking_no.visibility = View.GONE

        tv_title.text = "Do you want to create or join a team?"

        incrementVotesNeeded()
    }

    private fun getCurrentUser() {

        val ref = FirebaseDatabase.getInstance().getReference("/users").child("/${FirebaseAuth.getInstance().uid}")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                user = p0.getValue(User::class.java)!!

            }
        })
    }

    private fun joinNoTeam(user: User){

        val ref = FirebaseDatabase.getInstance().getReference("/users/${user.uid}/team")
        val team = Team("No Team", null, null, null, "")

        ref.setValue(team)

            .addOnSuccessListener {
                startActivity(Intent(this@TeamOptions, PageSelection::class.java))
            }
            .addOnFailureListener {

            }

    }

    private fun incrementVotesNeeded(){
        val votesRef = FirebaseDatabase.getInstance().getReference("/votesneeded")
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