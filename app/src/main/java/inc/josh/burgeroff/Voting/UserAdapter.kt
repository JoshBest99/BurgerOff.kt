package inc.josh.burgeroff.Voting

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.user_item.view.*
import java.util.*

class UserAdapter (val context : Context, val users: ArrayList<User>): RecyclerView.Adapter<UserViewHolder>(){

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.user_item, parent, false)
        return UserViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        var user = users.get(position)

        holder.view.userName.text = user.username
        Glide.with(context).load(user.profileImageUrl).into(holder.view.iv_profile)

        if(user.uid == FirebaseAuth.getInstance().uid || user.ratings!!.ratedUids.contains(FirebaseAuth.getInstance().uid!!)){
            holder.view.userName.setTextColor(Color.parseColor("#00FF00"))
        } else {
            holder.view.userName.setTextColor(Color.parseColor("#FF0000"))
        }

        holder.view.setOnClickListener {
            if(user.uid == FirebaseAuth.getInstance().uid){
                Toast.makeText(context, "You can't vote for yourself!", Toast.LENGTH_SHORT).show()
            }  else if(user.ratings!!.ratedUids.contains(FirebaseAuth.getInstance().uid!!)){
                Toast.makeText(context, "You've already voted for ${user.username}", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, VotingPage::class.java)
                var gson: Gson = Gson()
                var userData = gson.toJson(user)
                intent.putExtra("userData", userData)
                context.startActivity(intent)
            }

        }

    }


}

class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view)