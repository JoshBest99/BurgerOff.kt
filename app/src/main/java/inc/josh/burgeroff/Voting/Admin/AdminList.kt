package inc.josh.burgeroff.Voting.Admin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.PageSelection
import kotlinx.android.synthetic.main.activity_page_selection.*

class AdminList : AppCompatActivity(){
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_selection)

        recyclerView.layoutManager = LinearLayoutManager(this)

        getUserList()

        tv_profile.setOnClickListener {

            startActivity(Intent(this@AdminList, PageSelection::class.java))

        }

        tv_refresh.setOnClickListener {
            getUserList()
        }

        titleText.text = "Administration"

        titleText.setOnClickListener {
            startActivity(Intent(this@AdminList, AdminWinners::class.java))
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
                    recyclerView.adapter = AdminAdapter(context, userList)
                }
            }
        })


    }

}