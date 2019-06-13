package inc.josh.burgeroff.Voting.Admin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.admin_item.view.*


class AdminAdapter (val context : Context, val users: ArrayList<User>): RecyclerView.Adapter<AdminViewHolder>(){

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): AdminViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.admin_item, parent, false)
        return AdminViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {

        var user = users.get(position)

        holder.view.tv_username.text = user.username
        Glide.with(context).load(user.profileImageUrl).into(holder.view.iv_profile)

        holder.view.tv_patty.text = "Total Patty: ${user.ratings!!.pattyTaste}"
        holder.view.tv_taste.text = "Total Taste: ${user.ratings!!.burgerTaste}"
        holder.view.tv_looks.text = "Total Appearance ${user.ratings!!.appearance.toString()}"
        holder.view.tv_points.text = "Total Points ${user.ratings!!.appearance!! + user.ratings!!.burgerTaste!! + user.ratings!!.pattyTaste!!}"
    }

}

class AdminViewHolder(val view: View) : RecyclerView.ViewHolder(view)