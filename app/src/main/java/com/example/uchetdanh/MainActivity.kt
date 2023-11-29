package com.example.uchetdanh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.uchetdanh.navigation.Navigation
import com.example.uchetdanh.ui.theme.UchetDanhTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            UchetDanhTheme {}
                Navigation(navController = navController)

        }
    }
}
