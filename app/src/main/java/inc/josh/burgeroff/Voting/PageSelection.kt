package inc.josh.burgeroff.Voting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_page_selection.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import inc.josh.burgeroff.DataModels.User
import inc.josh.burgeroff.R

class PageSelection : AppCompatActivity(){

    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_selection)

        recyclerView.layoutManager = LinearLayoutManager(this)

        getUserList()
    }

    private fun getUserList(){
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
                   }

               }

                runOnUiThread {
                    recyclerView.adapter = UserAdapter(context, userList)
                }
            }
        })




    }
}