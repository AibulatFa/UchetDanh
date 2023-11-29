package com.example.uchetdanh.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.uchetdanh.Screens.*
import com.example.uchetdanh.firebase.AdminScreen
import com.example.uchetdanh.firebase.LoadingSpinnerAndNavigateScreen
import com.example.uchetdanh.firebase.UpdateScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Dobavit.route) { DobavitScreen(navController = navController) }
        composable(Screen.Documents.route) { DocumentsScreen(navController = navController) }
        composable(Screen.Otchet.route) { OtchetScreen(navController = navController) }
        composable(Screen.Qr.route) { QrScreen(navController = navController)}
        composable(Screen.Tovar.route) { TovarScreen(navController = navController) }
        composable(Screen.Start.route) { StartScreen(navController = navController) }
        composable(Screen.Registration.route) { RegistrationScreen(navController = navController) }
        composable(Screen.LoadingSpinnerAndNavigate.route) { LoadingSpinnerAndNavigateScreen(navController = navController) }
        composable(Screen.Update.route) { UpdateScreen(navController = navController) }
        composable(Screen.Profil.route) { ProfilScreen(navController = navController) }
        composable(Screen.Admin.route) { AdminScreen(navController = navController) }
        composable(Screen.Ad.route) { AdScreen(navController = navController) }


    }
}
