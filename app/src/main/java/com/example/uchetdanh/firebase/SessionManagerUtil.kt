package com.example.uchetdanh.firebase

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.uchetdanh.MainActivity
import com.example.uchetdanh.navigation.Screen
import java.util.Date

object SessionManagerUtil {
    private const val PREF_SESSION_EXPIRATION = "session_expiration"

    fun startUserSession(context: Context, expiresIn: Int) {
        val currentTime = Date()
        val expirationTime = Date(currentTime.time + expiresIn * 1000)

        val sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(PREF_SESSION_EXPIRATION, expirationTime.time)
        editor.apply()
    }

    fun isSessionActive(currentTime: Date, context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val expirationTime = sharedPreferences.getLong(PREF_SESSION_EXPIRATION, 0)

        return expirationTime > currentTime.time
    }

    fun endUserSession(context: Context, navController: NavController) {
        val sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(PREF_SESSION_EXPIRATION)
        editor.apply()

        navController.navigate(Screen.Login.route) // Переход на экран LoginScreen
    }
}