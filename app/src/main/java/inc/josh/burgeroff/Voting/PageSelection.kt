package inc.josh.burgeroff.Voting

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_page_selection.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.LoggingIn.LogIn
import inc.josh.burgeroff.R
import inc.josh.burgeroff.Voting.Admin.AdminWinners

class PageSelection : AppCompatActivity(){

    private val context = this
    private var adminEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_selection)

        recyclerView.layoutManager = LinearLayoutManager(this)

        getUserList()

        signOut.setOnClickListener {

            var sharedPreferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putString("email", "")
            editor.putString("password","")
            editor.apply()
            startActivity(Intent(this@PageSelection, LogIn::class.java))

        }

        refresh.setOnClickListener {
            getUserList()
        }

        titleText.setOnClickListener {
            if(adminEnabled){
                startActivity(Intent(this@PageSelection, AdminWinners::class.java))
            }
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

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
               p0.children.forEach {
                   Log.d("MainAct", it.toString())
                   val user = it.getValue(User::class.java)
                   if(user != null){
                       userList.add(user)
                       if(user.username.equals("adminready")) adminEnabled = true
                   }
               }

                runOnUiThread {
                    progressDialog.cancel()
                    recyclerView.adapter = UserAdapter(context, userList)
                }
            }
        })




    }
}