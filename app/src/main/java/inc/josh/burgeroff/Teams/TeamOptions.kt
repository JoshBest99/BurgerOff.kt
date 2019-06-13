package inc.josh.burgeroff.Teams

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.activity_team_options.*

class TeamOptions: AppCompatActivity(){

    private var isCooking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_options)

        btn_cooking_yes.setOnClickListener {
            isCooking = true
            changeTeamChoiceUI()
        }

        btn_cooking_no.setOnClickListener {
            isCooking = false
            changeTeamChoiceUI()
        }

        btn_create_team.setOnClickListener {
            val createTeamDialog = CreateTeamDialog(this)
            createTeamDialog.show()
        }

        btn_join_team.setOnClickListener {
            val joinTeamDialog = JoinTeamDialog(this)
            joinTeamDialog.show()
        }

    }

    private fun changeTeamChoiceUI(){
        btn_create_team.visibility = View.VISIBLE
        btn_join_team.visibility = View.VISIBLE

        btn_cooking_yes.visibility = View.GONE
        btn_cooking_no.visibility = View.GONE

        tv_title.text = "Do you want to create or join a team?"
    }
}