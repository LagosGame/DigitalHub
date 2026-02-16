package com.example.digitalhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digitalhub.presentation.ui.screen.LoginScreen

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
                    println("Login")
                },
                onRegisterSuccess = {
                    println("Navegar registro")
                }
            )

        }
    }

}