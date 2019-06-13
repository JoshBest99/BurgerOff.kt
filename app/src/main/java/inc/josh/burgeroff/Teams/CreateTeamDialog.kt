package inc.josh.burgeroff.Teams

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.dialog_create_team.*

class CreateTeamDialog (context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_create_team)

        btn_create.setOnClickListener {
            createTeam()
        }

    }

    private fun createTeam(){

    }


}