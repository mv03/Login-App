package com.example.loginapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_logged_in_avtivity.*

class LoggedInAvtivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in_avtivity)

        val sharedPref=this.getPreferences(Context.MODE_PRIVATE)?:return
        val isLogin=sharedPref.getString("Email","1")
        Log_Out.setOnClickListener{
            sharedPref.edit().remove("Email").apply()
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        if(isLogin=="1")
        {
            val email=intent.getStringExtra("email")
            if(email!=null){
                setText(email)
                with(sharedPref.edit())
                {
                    putString("Email",email)
                    apply()
                }
            }
            else{
                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else{
            setText(isLogin)
        }
    }
    private fun setText(Email:String?)
    {
        db= FirebaseFirestore.getInstance()
        if (Email != null) {
            db.collection("USERS").document(Email).get()
                .addOnSuccessListener { tasks->
                    name.text=tasks.get("Name").toString()
                    Phone_number.text=tasks.get("Phone").toString()
                    email_Logged_in.text=tasks.get("email").toString()
                }
        }
    }
}