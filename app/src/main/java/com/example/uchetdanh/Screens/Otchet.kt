package com.example.uchetdanh.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uchetdanh.R
import com.example.uchetdanh.navigation.Screen

@Composable
fun OtchetScreen(navController: NavHostController) {
    TopAppBar {

        IconButton(onClick = {navController.navigate(Screen.Start.route) }) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){


            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.add_box),
                    contentDescription = "Товар",
                    modifier = Modifier.size(60.dp)
                )
            }
            Text(text = "Отсканировать QR-код")
        }
    }
}
