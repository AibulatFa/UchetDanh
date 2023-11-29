package com.example.uchetdanh.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.uchetdanh.R
import com.example.uchetdanh.firebase.Product
import com.example.uchetdanh.navigation.Screen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Text


suspend fun getProductList(): List<Product> {
    val firestore = Firebase.firestore
    val productList = mutableListOf<Product>()

    firestore.collection("tovar").get().addOnSuccessListener { result ->
        for (document in result) {
            val name = document.getString("name") ?: ""
            val article = document.getString("article") ?: ""
            val model = document.getString("model") ?: ""
            val manufacturer = document.getString("manufacturer") ?: ""
            val price = document.getString("price") ?: ""
            val product = Product(name, article, model, manufacturer, price)
            productList.add(product)
        }
    }.await()

    return productList
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TovarScreen(navController: NavController) {
    var productList by remember { mutableStateOf(emptyList<Product>()) }

    LaunchedEffect(Unit) {
        val list = getProductList()
        productList = list
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Смотреть товар") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.Start.route) }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                }
            )
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (productList.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(productList) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = 4.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Артикул: ${product.article}",
                                    style = MaterialTheme.typography.body1
                                )
                                Text(
                                    text = "Модель: ${product.model}",
                                    style = MaterialTheme.typography.body1
                                )
                                Text(
                                    text = "Производитель: ${product.manufacturer}",
                                    style = MaterialTheme.typography.body1
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Цена: ${product.price}",
                                    style = MaterialTheme.typography.h6,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.primary
                                )
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "Нет доступных товаров",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}
