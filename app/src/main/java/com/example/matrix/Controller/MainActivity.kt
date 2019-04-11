package com.example.matrix.Controller

import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.matrix.R
import com.example.matrix.Services.AuthService
import com.example.matrix.Utilities.BROADCAST_USER_DATA_CHANGE
import com.example.matrix.Services.UserDataService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        hideKeyBoard()

        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReceiver, IntentFilter(
            BROADCAST_USER_DATA_CHANGE))

    }

    private val userDataChangeReceiver= object : BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?)
        {
            if(AuthService.isLoggedIn)
            {
                userNameNavHeader.text = UserDataService.name
                userEmailNavHeader.text = UserDataService.email
                val resourceId = resources.getIdentifier(UserDataService.avatarName,"drawable",packageName)
                userImageNavHeader.setImageResource(resourceId)
                userImageNavHeader.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
                loginBtnNavHeader.text = "LOGOUT"
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun loginBtnNavClicked(view: View)
    {
        if(AuthService.isLoggedIn)
        {
            //logout
            UserDataService.logOut()
            userNameNavHeader.text = ""
            userEmailNavHeader.text = ""
            userImageNavHeader.setImageResource(R.drawable.profiledefault)
            userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
            loginBtnNavHeader.text = "LOGIN"
        }
        else
        {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

    }

    fun addChannelClicked(view: View)
    {
        if(AuthService.isLoggedIn)
        {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.add_channel_dialog,null)

            builder.setView(dialogView)
                .setPositiveButton("Add")
                {
                        dialogInterface, i ->
                    //perform some logic when clicked
                    val nameTextField = dialogView.findViewById<EditText>(R.id.addChannelNametxt)
                    val descTextField = dialogView.findViewById<EditText>(R.id.addChannelDescTxt)
                    val channelName = nameTextField.text.toString()
                    val channelDesc = descTextField.text.toString()

                    //create channel
                    hideKeyBoard()
                }
                .setNegativeButton("Cancel")
                {
                    dialogInterface, i ->
                    //cancel and close the dialog
                    hideKeyBoard()
                }
                .show()
        }
    }

    fun sendMsgBtnClicked(view: View)
    {

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
