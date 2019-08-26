package inc.josh.burgeroff.Voting

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Looper
import inc.josh.burgeroff.DataModels.Team
import inc.josh.burgeroff.DataModels.User

class Controller{

    fun getTopTotalPoints(teams : ArrayList<Team>): Team{

        var winningTeam = Team()

        for (i in 0 until teams.size){
            if (i == 0) {
                winningTeam = teams[i]
            } else {
                val winningTeamScore = winningTeam.score!!
                val winningTeamScoreTotal = (winningTeamScore.appearance!!.toInt() + winningTeamScore.pattyTaste!!.toInt() + winningTeamScore.burgerTaste!!.toInt())

                val currentTeamScore = teams[i].score!!
                val currentTeamScoreTotal = (currentTeamScore.appearance!!.toInt() + currentTeamScore.pattyTaste!!.toInt() + currentTeamScore.burgerTaste!!.toInt())

                if(currentTeamScoreTotal > winningTeamScoreTotal){
                    winningTeam = teams[i]
                }
            }
        }

        return winningTeam
    }

    fun getTopTotalPatty(teams : ArrayList<Team>): Team{

        var winningTeam = Team()

        for (i in 0 until teams.size){
            if (i == 0) {
                winningTeam = teams[i]
            } else {
                if(teams[i].score!!.pattyTaste!!.toInt() > winningTeam.score!!.pattyTaste!!.toInt()){
                    winningTeam = teams[i]
                }
            }
        }

        return winningTeam
    }

    fun getTopTotalBurger(teams : ArrayList<Team>): Team{

        var winningTeam = Team()

        for (i in 0 until teams.size){
            if (i == 0) {
                winningTeam = teams[i]
            } else {
                if(teams[i].score!!.burgerTaste!!.toInt() > winningTeam.score!!.burgerTaste!!.toInt()){
                    winningTeam = teams[i]
                }
            }
        }

        return winningTeam
    }

    fun getTopTotalAppearance(teams : ArrayList<Team>): Team{

        var winningTeam = Team()

        for (i in 0 until teams.size){
            if (i == 0) {
                winningTeam = teams[i]
            } else {
                if(teams[i].score!!.appearance!!.toInt() > winningTeam.score!!.appearance!!.toInt()){
                    winningTeam = teams[i]
                }
            }
        }

        return winningTeam
    }

    fun showDialogWithMessage(message: String, context : Context) {
        if(Looper.myLooper() == null){
            Looper.prepare()
        }
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setMessage(message)
        dialogBuilder.setCancelable(true)

        dialogBuilder.setPositiveButton(
            "Okay",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = dialogBuilder.create()
        alert.show()
    }


}