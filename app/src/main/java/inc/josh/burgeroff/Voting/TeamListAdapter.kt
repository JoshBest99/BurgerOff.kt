package inc.josh.burgeroff.Voting

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.UIViewController
import kotlinx.android.synthetic.main.user_item.view.*
import java.util.*
import android.widget.LinearLayout
import inc.josh.burgeroff.R

class TeamListAdapter (val context : Context, val teamList: ArrayList<Team>): RecyclerView.Adapter<UserViewHolder>(){

    private val uiViewController = UIViewController()

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.user_item, parent, false)
        return UserViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        var team = teamList.get(position)

        holder.view.tv_username.text = team.name

        Glide.with(context).load(team.members!![0].profileImageUrl).into(holder.view.iv_profile)

        if(team.members!!.size == 2){
//
//            val scale = context.resources.displayMetrics.density
//
//            val layoutParams = LinearLayout.LayoutParams((35 * scale).toInt(), (35 * scale).toInt())
//            holder.view.iv_profile.layoutParams = layoutParams
//            holder.view.iv_profile_two.layoutParams = layoutParams

            holder.view.iv_profile_two.visibility = View.VISIBLE

            Glide.with(context).load(team.members!![1].profileImageUrl).into(holder.view.iv_profile_two)

        }

        if(canVoteForThisTeam(team, false)){
            holder.view.tv_username.setTextColor(Color.parseColor("#FF0000"))
        } else {
            holder.view.tv_username.setTextColor(Color.parseColor("#00FF00"))
        }

        holder.view.setOnClickListener {
            if(canVoteForThisTeam(team,true)){
                context.startActivity(Intent(context, VotingPage::class.java).putExtra("team", team))
            }

        }

    }

    private fun canVoteForThisTeam(team: Team, isClickEvent: Boolean): Boolean{

        if(team.voteesUids.contains(FirebaseAuth.getInstance().uid!!)){
            if(isClickEvent) uiViewController.createDialogWithMessage("You can't vote for ${team.name} twice!")
            return false
        }

        for (i in 0 until team.members!!.size){
            if(team.members!![i].uid.equals(FirebaseAuth.getInstance().uid)){
                if(isClickEvent)uiViewController.createDialogWithMessage("You can't vote for your own team!")
                return false
            }



        }

        return true

    }


}

class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)