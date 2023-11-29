package com.example.uchetdanh.firebase

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore



private fun deleteUser(user: Users, db: FirebaseFirestore) {
    db.collection("users").document(user.email)
        .delete()
        .addOnSuccessListener {

        }
        .addOnFailureListener { e ->

        }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdminScreen(navController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    val users = remember { mutableStateListOf<Users>() }

    LaunchedEffect(Unit) {
        // Fetch all users from the Firestore database
        db.collection("users").get().addOnSuccessListener { result ->
            users.clear()
            for (document in result) {
                val user = document.toObject(Users::class.java)
                users.add(user)
            }
        }
    }

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
        Column(modifier = Modifier.padding(16.dp)) {
            Text("User List", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(16.dp))

            if (users.isNotEmpty()) {
                LazyColumn {
                    items(users) { user ->
                        UserItem(user) {
                            deleteUser(user, db)
                        }
                        Divider()
                    }
                }
            } else {
                Text("No users found.")
            }
        }
    }
}

@Composable
fun UserItem(user: Users, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray
                )
            }
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}
