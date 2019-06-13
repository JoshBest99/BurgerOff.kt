package inc.josh.burgeroff.Teams

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.UserViewHolder
import kotlinx.android.synthetic.main.item_team.view.*
import java.util.ArrayList

class JoinTeamAdapter(val context : Context): RecyclerView.Adapter<UserViewHolder>(){

    override fun getItemCount(): Int {
        //return teams.size
        return 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_team, parent, false)
        return UserViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.view.tv_team_name.text = "Team $position"

        holder.view.btn_join.setOnClickListener {
            //joinTeam(team.id)
        }

    }

    private fun joinTeam(id: String){

    }


}
