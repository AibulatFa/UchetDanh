package com.example.uchetdanh.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uchetdanh.R
import com.example.uchetdanh.firebase.Singleton
import com.example.uchetdanh.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdScreen(navController: NavHostController) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Screen") },
                backgroundColor = Color(0xFF6200EE),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

                .wrapContentSize(Alignment.Center)
        ) {
            var email by remember { mutableStateOf("") }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Login") })


            var password by rememberSaveable { mutableStateOf("") }
            var passwordVisibility by remember { mutableStateOf(false) }

            val icon = if (passwordVisibility)
                painterResource(id = R.drawable.visibility)
            else
                painterResource(id = R.drawable.visibility_off)

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                placeholder = { Text(text = "Password") },
                label = { Text(text = "Password") },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(
                            painter = icon,
                            contentDescription = "Visibility Icon"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    loginUser(context, email, password, navController = navController)
                    Singleton.email = email
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF6200EE)
                ),
                // Uses ButtonDefaults.ContentPadding by default

                contentPadding = PaddingValues(
                    start = 101.dp,
                    top = 12.dp,
                    end = 101.dp,
                    bottom = 12.dp

                )
            ) {


                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Icon(Icons.Filled.Person, contentDescription = "Войти")
                Text("Войти", color = Color(0xFF03DAC5))
            }
        }
    }
}

private fun loginUser(context: Context, email: String, password: String, navController: NavHostController) {
    val firestore = FirebaseFirestore.getInstance()
    val usersCollection = firestore.collection("admin")

    if (email.isNotEmpty() && password.isNotEmpty()) {
        usersCollection
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result?.documents
                    if (documents != null && documents.isNotEmpty()) {
                        // Вход выполнен успешно
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.Admin.route)
                    } else {
                        // Неправильный email или пароль
                        Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Ошибка при выполнении запроса к Firestore
                    Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    } else {
        // Не заполнены все поля
        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
    }
}

