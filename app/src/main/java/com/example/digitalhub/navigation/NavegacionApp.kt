package com.example.digitalhub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.digitalhub.presentation.ui.screen.BibliotecaScreen
import com.example.digitalhub.presentation.ui.screen.CartaDetalleScreen
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
                    navController.navigate("biblioteca")
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

        composable("biblioteca") {
            BibliotecaScreen(
                onBack = {
                    navController.popBackStack()
                },
                onCartaClick = { cartaId ->
                    navController.navigate("carta/$cartaId")

                }
            )
        }
        composable(
            route = "carta/{cartaId}",
            arguments = listOf(
                navArgument("cartaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val cartaId = backStackEntry.arguments?.getString("cartaId") ?: ""

            CartaDetalleScreen(
                cartaId = cartaId,
                onBack = {
                    navController.popBackStack()
                },
                onAddToDeck = {
                    println("Añadir carta $cartaId a mazo")
                }
            )
        }

    }
}
