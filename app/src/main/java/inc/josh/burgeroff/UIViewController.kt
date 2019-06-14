package inc.josh.burgeroff

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class UIViewController(val context : Context) {

    fun createDialogWithMessage(message: String){

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