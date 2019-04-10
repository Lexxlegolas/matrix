package com.example.matrix.Services

import android.graphics.Color
import java.util.*

object UserDataService
{
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun logOut()
    {
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        AuthService.authToken = ""
        AuthService.userEmail = ""
        AuthService.isLoggedIn = false
    }

    fun returnAvatarColor(components:String) : Int
    {
        //[0.9254901960784314, 0.7411764705882353, 0.5372549019607843, 1]
        //0.9254901960784314 0.7411764705882353 0.5372549019607843 1

        val strippedColor = components
            .replace("[","")
            .replace("]","")
            .replace(",","")

        var r = 0
        var g = 0
        var b = 0

        val scanner = Scanner(strippedColor)
        if(scanner.hasNext())
        {
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()

        }
        return Color.rgb(r,g,b)
    }


}