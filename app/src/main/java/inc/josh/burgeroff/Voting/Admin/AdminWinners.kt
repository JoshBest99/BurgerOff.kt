package inc.josh.burgeroff.Voting.Admin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.Controller
import inc.josh.burgeroff.Voting.PageSelection
import kotlinx.android.synthetic.main.activity_page_selection.*
import kotlinx.android.synthetic.main.activity_winners.*

class AdminWinners : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winners)

        getUserList()

        tv_back.setOnClickListener {
            onBackPressed()
        }

        tv_next.setOnClickListener {
            startActivity(Intent(this@AdminWinners, AdminList::class.java))
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
                }

                setUI(teams)

            }
        })


    }

    private fun setUI(teams: ArrayList<Team>){
        var controller = Controller()
        totalPointsUI(controller.getTopTotalPoints(teams))
        totalPattyUI(controller.getTopTotalPatty(teams))
        totalBurgerUI(controller.getTopTotalBurger(teams))
        totalAppearanceUI(controller.getTopTotalAppearance(teams))

    }

    private fun totalPointsUI(team: Team){
        totalPointWinnerName.text = team.name
        totalPointWinnerAmount.text = "Points: ${team.score!!.appearance!!.toInt() + team.score!!.pattyTaste!!.toInt() + team.score!!.burgerTaste!!.toInt()}"
    }

    private fun totalPattyUI(team: Team){
        totalPattyWinnerName.text = team.name
        totalPattyWinnerAmount.text = "Points: ${team.score!!.pattyTaste}"
    }

    private  fun totalBurgerUI(team: Team){
        totalOverallWinnerName.text = team.name
        totalOverallWinnerAmount.text = "Points: ${team.score!!.burgerTaste}"
    }

    private fun totalAppearanceUI(team: Team){
        totalAppearanceWinnerName.text = team.name
        totalAppearanceWinnerAmount.text = "Points: ${team.score!!.appearance}"
    }
}