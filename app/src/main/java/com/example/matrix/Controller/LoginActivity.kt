package com.example.matrix.Controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.matrix.R
import com.example.matrix.Services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginSpinner.visibility = View.INVISIBLE
    }

    fun loginLoginBtnClicked(view: View)
    {
        enableSpinner(true)
        val email = loginEmailTxt.text.toString()
        val password = loginPasswordTxt.text.toString()
        hideKeyBoard()

        if(email.isNotEmpty() && password.isNotEmpty())
        {
            AuthService.loginUser(this,email,password)
            {loginSuccess ->
                if(loginSuccess)
                {
                    AuthService.findUserByEmail(this)
                    {findSuccess ->
                        if(findSuccess)
                        {
                            enableSpinner(false)
                            finish()
                        }else
                        {
                            errorToast()
                        }

                    }
                }else
                {
                    errorToast()
                }

            }
        }else{
            Toast.makeText(this,"please fill in both email and password",Toast.LENGTH_LONG).show()
        }


    }
    fun loginCreateUserBtnClicked(view: View)
    {
        val create = Intent(this, CreateUserActivity::class.java)
        startActivity(create)
        finish()
    }

    fun errorToast()
    {
        Toast.makeText(this,"Something went wrong, please try again.", Toast.LENGTH_SHORT).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean)
    {
        if (enable)
        {
            loginSpinner.visibility = View.VISIBLE
        }
        else
        {
            loginSpinner.visibility = View.INVISIBLE
        }
        loginLoginBtn.isEnabled = !enable
        loginCreateUserBtn.isEnabled = !enable
    }

    fun hideKeyBoard()
    {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManager.isAcceptingText)
        {
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
        }
    }
}
