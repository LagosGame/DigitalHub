package com.example.digitalhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digitalhub.presentation.ui.screen.LoginScreen
import com.example.digitalhub.presentation.ui.screen.MainScreen

@Composable
fun NavegacionApp(
    navController : NavHostController = rememberNavController()
){

    NavHost(
        navController=navController,
        startDestination="login"
    )
    {
        composable("login"){
            LoginScreen(
                onLoginSuccess = {username->
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    println("Navegar registro")
                }
            )

        }
        composable("home") {
            MainScreen(
                onBiblioteca = {
                    println("Navegar a Biblioteca")
                    // TODO: navController.navigate("biblioteca")
                },
                onConstruir = {
                    println("Navegar a Construir mazo")
                    // TODO: navController.navigate("construir")
                },
                onLista = {
                    println("Navegar a Lista de mazos")
                    // TODO: navController.navigate("lista")
                },
                onPerfil = {
                    println("Navegar a Perfil")
                    // TODO: navController.navigate("perfil")
                }
            )
        }
    }

}