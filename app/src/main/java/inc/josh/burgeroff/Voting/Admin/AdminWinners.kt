package inc.josh.burgeroff.Voting.Admin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.Controller
import inc.josh.burgeroff.Voting.PageSelection
import kotlinx.android.synthetic.main.activity_winners.*

class AdminWinners : AppCompatActivity(){
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winners)

        getUserList()

        titleText.setOnClickListener {
            startActivity(Intent(this@AdminWinners, AdminList::class.java))
        }

        signOut.setOnClickListener {

            startActivity(Intent(this@AdminWinners, PageSelection::class.java))

        }

        tv_refresh.setOnClickListener {
            getUserList()
        }

    }

    private fun getUserList(){
        val progressDialog : ProgressDialog = ProgressDialog(this)
        progressDialog.setMessage("Getting Contestants...")

        runOnUiThread {
            progressDialog.show()
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users")
        var userList : ArrayList<User> = ArrayList()

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    Log.d("MainAct", it.toString())
                    val user = it.getValue(User::class.java)
                    if(user != null){
                        userList.add(user)
                    }

                }

                runOnUiThread {
                    progressDialog.cancel()
                }

                setUI(userList)

            }
        })


    }

    private fun setUI(usersList : ArrayList<User>){
        var controller : Controller = Controller()
        totalPointsUI(controller.getTopTotalPoints(usersList))
        totalPattyUI(controller.getTopTotalPatty(usersList))
        totalBurgerUI(controller.getTopTotalBurger(usersList))
        totalAppearanceUI(controller.getTopTotalAppearance(usersList))

    }

    private fun totalPointsUI(user: User){
//        totalPointWinnerName.text = user.username
//        totalPointWinnerAmount.text = "Points: ${user.ratings!!.appearance!! + user.ratings!!.pattyTaste!! + user.ratings!!.burgerTaste!!}"
    }

    private fun totalPattyUI(user: User){
//        totalPattyWinnerName.text = user.username
//        totalPattyWinnerAmount.text = "Points: ${user.ratings!!.pattyTaste}"
    }

    private  fun totalBurgerUI(user: User){
//        totalOverallWinnerName.text = user.username
//        totalOverallWinnerAmount.text = "Points: ${user.ratings!!.burgerTaste}"
    }

    private fun totalAppearanceUI(user: User){
//        totalAppearanceWinnerName.text = user.username
//        totalAppearanceWinnerAmount.text = "Points: ${user.ratings!!.appearance}"
    }
}