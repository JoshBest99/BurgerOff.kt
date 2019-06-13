package inc.josh.burgeroff.Teams

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.TeamListAdapter
import kotlinx.android.synthetic.main.activity_page_selection.*
import kotlinx.android.synthetic.main.dialog_join_team.*

class JoinTeamDialog(context: Context, val currentUser: User) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_join_team)
        init()

    }

    private fun init(){
        rv_vertical.layoutManager = LinearLayoutManager(context)
        getTeamList()
    }

    private fun getTeamList(){

        val ref = FirebaseDatabase.getInstance().getReference("/teams")
        var teamList : ArrayList<Team> = ArrayList()

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val team = it.getValue(Team::class.java)
                    teamList.add(team!!)
                    rv_vertical.adapter = JoinTeamAdapter(context, teamList, currentUser)
                }

            }
        })

    }


}