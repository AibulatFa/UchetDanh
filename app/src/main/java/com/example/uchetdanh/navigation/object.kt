package com.example.uchetdanh.navigation

sealed class Screen(val route: String){
    object Start: Screen("Start")
    object Tovar: Screen("Tovar")
    object Documents: Screen("Documents")
    object Profil: Screen("Person")
    object Dobavit: Screen("Dobavit")
    object Otchet: Screen("Otchet")
    object Qr: Screen("Qr")
    object Login: Screen("Login")
    object Registration: Screen("Registration")
    object LoadingSpinnerAndNavigate: Screen("LoadingSpinnerAndNavigate")
    object Update: Screen("Update")
    object Admin: Screen("Admin")
    object Ad: Screen("Ad")

}