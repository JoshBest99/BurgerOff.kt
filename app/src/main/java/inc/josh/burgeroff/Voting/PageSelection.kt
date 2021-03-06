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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_selection)
        init()

        tv_refresh.setOnClickListener {
            init()
        }

        tv_winners.setOnClickListener {
            startActivity(Intent(this@PageSelection, AdminWinners::class.java))
        }

    }

    override fun onBackPressed() {
    }

    private fun init(){

        recyclerView.layoutManager = LinearLayoutManager(this)
        getTeamList()
        isReadyToVote()

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
                   }
               }

                runOnUiThread {
                    progressDialog.cancel()
                    recyclerView.adapter = TeamListAdapter(this@PageSelection, teamList)
                }
            }
        })

    }

    private fun isReadyToVote(){

        val votesRef = FirebaseDatabase.getInstance().getReference("/votesneeded")
        var votesNeeded: Int

        votesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                votesNeeded = p0.getValue(Int::class.java)!!

                tv_winners.isEnabled = votesNeeded == 1

            }
        })
    }

}