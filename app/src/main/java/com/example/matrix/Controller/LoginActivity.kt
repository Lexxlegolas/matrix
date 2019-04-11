package com.example.matrix.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.matrix.R
import com.example.matrix.Services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginLoginBtnClicked(view: View)
    {
        val email = loginEmailTxt.text.toString()
        val password = loginPasswordTxt.text.toString()

        AuthService.loginUser(this,email,password)
        {loginSuccess ->
            if(loginSuccess)
            {
                AuthService.findUserByEmail(this)
                {findSuccess ->
                    if(findSuccess)
                    {
                        finish()
                    }

                }
            }

        }
    }
    fun loginCreateUserBtnClicked(view: View)
    {
        val create = Intent(this, CreateUserActivity::class.java)
        startActivity(create)
        finish()
    }
}
