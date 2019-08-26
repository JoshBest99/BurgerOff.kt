package inc.josh.burgeroff.Voting.Admin

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.activity_overall_winners.*

class AdminList : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overall_winners)

        recyclerView.layoutManager = LinearLayoutManager(this)
        getUserList()

        tv_back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun getUserList(){
        val progressDialog : ProgressDialog = ProgressDialog(this)
        progressDialog.setMessage("Getting Contestants...")

        runOnUiThread {
            progressDialog.show()
        }

        val ref = FirebaseDatabase.getInstance().getReference("/teams")
        var teams = ArrayList<Team>()

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    Log.d("MainAct", it.toString())
                    val team = it.getValue(Team::class.java)
                    if(team != null){
                        teams.add(team)
                    }
                }

                runOnUiThread {
                    progressDialog.cancel()
                    recyclerView.adapter = AdminAdapter(this@AdminList, teams)
                }
            }
        })


    }

}