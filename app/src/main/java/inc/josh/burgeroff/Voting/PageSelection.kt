package inc.josh.burgeroff.Voting

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_page_selection.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.Admin.AdminWinners

class PageSelection : AppCompatActivity(){

    private val context = this
    private var adminEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_selection)

        recyclerView.layoutManager = LinearLayoutManager(this)

        getTeamList()

        tv_profile.setOnClickListener {

            startActivity(Intent(this@PageSelection, Profile::class.java))

        }

        tv_refresh.setOnClickListener {
            getTeamList()
        }

        titleText.setOnClickListener {
            if(adminEnabled){
                startActivity(Intent(this@PageSelection, AdminWinners::class.java))
            }
        }

    }

    private fun getTeamList(){
        val progressDialog : ProgressDialog = ProgressDialog(this)
        progressDialog.setMessage("Getting Contestants...")

        runOnUiThread {
            progressDialog.show()
        }

        val ref = FirebaseDatabase.getInstance().getReference("/teams")
        var teamList : ArrayList<Team> = ArrayList()

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
               p0.children.forEach {
                   Log.d("MainAct", it.toString())
                   val team = it.getValue(Team::class.java)
                   if(team != null){
                       teamList.add(team)
                       //if(user.username.equals("adminready")) adminEnabled = true
                   }
               }

                runOnUiThread {
                    progressDialog.cancel()
                    recyclerView.adapter = TeamListAdapter(context, teamList)
                }
            }
        })




    }
}