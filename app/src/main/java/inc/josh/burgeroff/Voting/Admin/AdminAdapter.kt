package inc.josh.burgeroff.Voting.Admin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.admin_item.view.*


class AdminAdapter (val context : Context, val teams: ArrayList<Team>): RecyclerView.Adapter<AdminViewHolder>(){

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): AdminViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.admin_item, parent, false)
        return AdminViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {

        var team = teams[position]

        holder.view.tv_username.text = team.name

        holder.view.tv_patty.text = "Total Patty: ${team.score!!.pattyTaste}"
        holder.view.tv_taste.text = "Total Taste: ${team.score!!.burgerTaste}"
        holder.view.tv_looks.text = "Total Appearance ${team.score!!.appearance.toString()}"
        holder.view.tv_points.text = "Total Points ${team.score!!.appearance!!.toInt() + team.score!!.burgerTaste!!.toInt() + team.score!!.pattyTaste!!.toInt()}"
    }

}

class AdminViewHolder(val view: View) : RecyclerView.ViewHolder(view)