package com.example.uchetdanh.Screens


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.uchetdanh.firebase.SessionManagerUtil
import com.example.uchetdanh.firebase.Singleton
import com.example.uchetdanh.firebase.Users
import com.example.uchetdanh.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date


@Composable
fun ProfilScreen(navController: NavHostController) {
    val context = LocalContext.current
    val currentTime = Date()
    val isSessionActive = SessionManagerUtil.isSessionActive(currentTime, context)


    val firestore = FirebaseFirestore.getInstance()
    val currentUserRef = firestore.collection("users").document(Singleton.email)
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }


    var userName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        currentUserRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                userName = document.getString("username") ?: ""
            }
        }.addOnFailureListener { exception ->
            // Обработка ошибки получения данных из Firestore
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Профиль пользователя",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 11.dp)
        )

        Text(
            text = userName,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = telephone,
            onValueChange = { telephone = it },
            label = { Text("telephone") },
            modifier = Modifier.fillMaxWidth()
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {

            Button(
                onClick = {
                    val db = Firebase.firestore
                    val prov = db.collection("users").document(Singleton.email)
                    prov.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                var user = Users(
                                    email = Singleton.email,
                                    username = name,
                                    password = password,
                                    telephone = telephone

                                )
                                db.collection("users").document(Singleton.email)
                                    .set(user)
                                    .addOnSuccessListener {


                                    }
                                    .addOnFailureListener {
                                    }

                            }
                        }
                    SessionManagerUtil.endUserSession(context, navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Изменить данные",
                    color = Color.White,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}



@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current

    TopAppBar( backgroundColor = Color(0xFF6200EE)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            TextButton(onClick = { navController.navigate(Screen.Login.route) }, modifier = Modifier.weight(1f)) {
                Text(
                    "Login",
                    color = Color(0xFF03DAC5 )
                )
            }


            TextButton(onClick = { navController.navigate(Screen.Registration.route) }, modifier = Modifier.weight(1f)) {
                Text(
                    "Registration",
                    color = Color(0xFF03DAC5)
                )
            }

        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()

            .wrapContentSize(Alignment.Center)
    ) {
        Image(
            painter = painterResource(id = R.drawable.uchett),
            contentDescription = "Logo",
            modifier = Modifier
                .size(170.dp)
                .align(Alignment.CenterHorizontally)

        )

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

            onClick = {val expiresIn = 30
                SessionManagerUtil.startUserSession(context, expiresIn)
                loginUser(context, email, password,  navController = navController)
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
            Text("Войти",  color = Color(0xFF03DAC5))
        }
    }
}

private fun loginUser(context: Context, email: String, password: String, navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.LoadingSpinnerAndNavigate.route)
                } else {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    } else {
        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
    }
}




@Composable
fun RegistrationScreen(navController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(backgroundColor = Color(0xFF6200EE)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                TextButton(
                    onClick = { navController.navigate(Screen.Login.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Login",
                        color = Color(0xFF03DAC5)
                    )
                }

                TextButton(
                    onClick = { navController.navigate(Screen.Registration.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "Registration",
                        color = Color(0xFF03DAC5)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.uchett),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(170.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                var name by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Имя") }
                )

                var telephone by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = telephone,
                    onValueChange = { telephone = it },
                    label = { Text("Telephone") }
                )

                var email by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )

                var password by rememberSaveable { mutableStateOf("") }
                var passwordVisibility by remember { mutableStateOf(false) }

                val icon = if (passwordVisibility)
                    painterResource(id = R.drawable.visibility)
                else
                    painterResource(id = R.drawable.visibility_off)

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
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

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {registerUser(context,name = name, telephone = telephone,email = email, password = password, navController = navController)
                        Singleton.email = email

                    },

                    // Uses ButtonDefaults.ContentPadding by default
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF6200EE)
                    ),
                    contentPadding = PaddingValues(
                        start = 70.dp,
                        top = 12.dp,
                        end = 70.dp,
                        bottom = 12.dp


                    )


                ) {


                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(Icons.Filled.Person, contentDescription = "Регистрация")
                    Text("Регистрация   ", color = Color(0xFF03DAC5))

                }
            }
        }
    }
}



private fun registerUser(context: Context, name: String, telephone: String, email: String, password: String, navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    if (email.isNotEmpty() && password.isNotEmpty() && telephone.isNotEmpty() && name.isNotEmpty()) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show()
                    db.collection("users").document(email)
                        .set(Users(name,telephone,email,password))
                    navController.navigate(Screen.Login.route)

                } else {
                    Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }
    } else {
        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
    }

}