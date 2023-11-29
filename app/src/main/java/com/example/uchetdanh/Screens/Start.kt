package com.example.uchetdanh.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import com.example.uchetdanh.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uchetdanh.navigation.Screen

@Composable
fun StartScreen(navController: NavHostController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Добро пожаловать",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 11.dp)
            )


            Row {
                IconButton(onClick = {
                    navController.navigate(Screen.Tovar.route)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.content),
                        contentDescription = "Товар",
                        modifier = Modifier.size(60.dp)
                    )
                }

                IconButton(onClick = {
                    navController.navigate(Screen.Documents.route)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.file),
                        contentDescription = "Документы",
                        modifier = Modifier.size(60.dp)
                    )
                }
                IconButton(onClick = {
                    navController.navigate(Screen.Profil.route)
                }) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Профиль",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }



            Spacer(modifier = Modifier.height(20.dp))
            Row {
                IconButton(onClick = {
                    navController.navigate(Screen.Dobavit.route)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        contentDescription = "Добавить",
                        modifier = Modifier.size(60.dp)
                    )
                }
                IconButton(onClick = {
                    navController.navigate(Screen.Qr.route)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.camera),
                        contentDescription = "Qr",
                        modifier = Modifier.size(60.dp)
                    )
                }
                IconButton(onClick = {
                    navController.navigate(Screen.Ad.route)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = "Qr",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        }
    }
}

