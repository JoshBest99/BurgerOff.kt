package inc.josh.burgeroff.Voting

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Looper
import inc.josh.burgeroff.DataModels.User

class Controller{

    fun getTopTotalPoints(users : ArrayList<User>): User{

        var endUser: User = User()

//        for ((index, user: User) in users.withIndex()){
//
//            if(index == 0){
//                endUser = user
//            } else {
//                if((user.ratings!!.appearance!! + user.ratings!!.pattyTaste!! + user.ratings!!.burgerTaste!!)
//                    > (endUser.ratings!!.appearance!! + endUser.ratings!!.pattyTaste!! + endUser.ratings!!.burgerTaste!!)){
//                    endUser = user
//                }
//            }
//        }

        return endUser
    }

    fun getTopTotalPatty(users : ArrayList<User>): User{

        var endUser: User = User()

//        for ((index, user: User) in users.withIndex()){
//
//            if(index == 0){
//                endUser = user
//            } else {
//                if(user.ratings!!.pattyTaste!! > endUser.ratings!!.pattyTaste!!){
//                    endUser = user
//                }
//            }
//        }

        return endUser
    }

    fun getTopTotalBurger(users : ArrayList<User>): User{

        var endUser: User = User()
//
//        for ((index, user: User) in users.withIndex()){
//
//            if(index == 0){
//                endUser = user
//            } else {
//                if(user.ratings!!.burgerTaste!! > endUser.ratings!!.burgerTaste!!){
//                    endUser = user
//                }
//            }
//        }

        return endUser
    }

    fun getTopTotalAppearance(users : ArrayList<User>): User{

        var endUser: User = User()

//        for ((index, user: User) in users.withIndex()){
//
//            if(index == 0){
//                endUser = user
//            } else {
//                if(user.ratings!!.appearance!! > endUser.ratings!!.appearance!!){
//                    endUser = user
//                }
//            }
//        }

        return endUser
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