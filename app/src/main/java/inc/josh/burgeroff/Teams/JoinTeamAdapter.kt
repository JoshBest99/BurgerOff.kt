package inc.josh.burgeroff.Teams

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import inc.josh.burgeroff.DataModels.Score
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.PageSelection
import inc.josh.burgeroff.Voting.UserViewHolder
import kotlinx.android.synthetic.main.dialog_create_team.*
import kotlinx.android.synthetic.main.item_team.view.*
import java.util.*

class JoinTeamAdapter(val context : Context, private val teams: ArrayList<Team>, val currentUser: User): RecyclerView.Adapter<UserViewHolder>(){

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_team, parent, false)
        return UserViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val team = teams[position]
        holder.view.tv_team_name.text = team.name

        holder.view.btn_join.setOnClickListener {
            if(team.members!!.size != 2){
                updateTeam(team)
            }
        }

    }

    private fun updateTeam(team: Team){

        team.members!!.add(currentUser)

        val ref = FirebaseDatabase.getInstance().getReference("/teams/${team.uid}")

        ref.setValue(team)

            .addOnSuccessListener {
                updateUser(team)
            }
            .addOnFailureListener {

            }

    }

    private fun updateUser(team : Team){

        val ref = FirebaseDatabase.getInstance().getReference("/users/${currentUser.uid}/team")

        ref.setValue(team)

            .addOnSuccessListener {
                context.startActivity(Intent(context, PageSelection::class.java))
            }
            .addOnFailureListener {

            }

    }

}
