package com.example.digitalhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digitalhub.presentation.ui.screen.LoginScreen
import com.example.digitalhub.presentation.ui.screen.MainScreen
import com.example.digitalhub.presentation.ui.screen.RegisterScreen

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
                    navController.navigate("register")
                }
            )

        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
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
                })
        }
        composable("register"){
            RegisterScreen(
                onRegisterSuccess = { username->
                    println("Register")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}
