package inc.josh.burgeroff.Teams

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import inc.josh.burgeroff.R
import kotlinx.android.synthetic.main.dialog_join_team.*

class JoinTeamDialog(context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_join_team)
        init()

    }

    private fun getTeamList(){
        rv_vertical.adapter = JoinTeamAdapter(context)
    }

    private fun init(){
        rv_vertical.layoutManager = LinearLayoutManager(context)
        getTeamList()
    }
}