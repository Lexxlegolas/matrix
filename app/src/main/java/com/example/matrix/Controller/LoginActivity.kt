package com.example.matrix.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.matrix.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginLoginBtnClicked(view: View)
    {

    }
    fun loginCreateUserBtnClicked(view: View)
    {
        val create = Intent(this, CreateUserActivity::class.java)
        startActivity(create)
    }
}
