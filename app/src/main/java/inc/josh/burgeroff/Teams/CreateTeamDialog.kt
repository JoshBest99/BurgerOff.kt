package inc.josh.burgeroff.Teams

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.Score
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.PageSelection
import kotlinx.android.synthetic.main.dialog_create_team.*
import java.util.*
import kotlin.collections.ArrayList

class CreateTeamDialog (context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_create_team)

        btn_create.setOnClickListener {
            getCurrentUser()
        }

    }

    private fun createTeam(user: User){

        val team = Team(et_teamname.text.toString(), UUID.randomUUID().toString(), Score("0", "0", "0"), ArrayList<User>().also { it.add(user) }, "")

        val ref = FirebaseDatabase.getInstance().getReference("/teams/${team.uid}")

        ref.setValue(team)

            .addOnSuccessListener {
                context.startActivity(Intent(context, PageSelection::class.java))
            }
            .addOnFailureListener {

            }

    }

    private fun getCurrentUser(){

        val ref = FirebaseDatabase.getInstance().getReference("/users").child("/${FirebaseAuth.getInstance().uid}")
        var user : User? = null

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                user = p0.getValue(User::class.java)
                createTeam(user!!)
            }
        })

    }


}