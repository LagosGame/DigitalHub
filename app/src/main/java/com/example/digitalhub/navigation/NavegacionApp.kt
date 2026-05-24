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
import com.example.digitalhub.presentation.ui.screen.ComentariosScreen
import com.example.digitalhub.presentation.ui.screen.ConstruirMazoScreen
import com.example.digitalhub.presentation.ui.screen.CrearMazoScreen
import com.example.digitalhub.presentation.ui.screen.DetalleMazoScreen
import com.example.digitalhub.presentation.ui.screen.ListaMazosScreen
import com.example.digitalhub.presentation.ui.screen.LoginScreen
import com.example.digitalhub.presentation.ui.screen.MainScreen
import com.example.digitalhub.presentation.ui.screen.PerfilScreen
import com.example.digitalhub.presentation.ui.screen.RecuperarPasswordScreen
import com.example.digitalhub.presentation.ui.screen.RegisterScreen
import com.example.digitalhub.presentation.ui.screen.TierListScreen
import com.example.digitalhub.presentation.ui.screen.VerMazoScreen

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
                },
                onNavigateToRecuperarPassword = {
                    navController.navigate("recuperar_password")
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
        composable("recuperar_password") {
            RecuperarPasswordScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("home") {
            MainScreen(
                onBiblioteca = {
                    navController.navigate("biblioteca")
                },
                onConstruir = {
                    navController.navigate("construir_mazo")
                },
                onLista = {
                    navController.navigate("lista_mazos")
                },
                onPerfil = {
                    navController.navigate("perfil")
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
                onBack = { navController.popBackStack() },
                onCartaClick = { cartaId ->
                    navController.navigate("carta/$cartaId")
                },
                shouldReload = false
            )
        }
        composable("construir_mazo") {
            ConstruirMazoScreen(
                onBack = {
                    navController.popBackStack()
                },
                onCrearMazo = {
                    navController.navigate("crear_mazo")
                },
                onMazoClick = { mazoId ->
                    navController.navigate("crear_mazo?mazoId=$mazoId")
                }
            )
        }
        composable(
            route = "crear_mazo?mazoId={mazoId}",
            arguments = listOf(
                navArgument("mazoId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val mazoId = backStackEntry.arguments?.getString("mazoId")

            CrearMazoScreen(
                mazoId = mazoId,
                onBack = {
                    navController.popBackStack()
                },
                onNavToDetalle = { id ->
                    navController.navigate("detalle_mazo/$id")
                }
            )
        }
        composable(
            route = "detalle_mazo/{mazoId}",
            arguments = listOf(
                navArgument("mazoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val mazoId = backStackEntry.arguments?.getString("mazoId") ?: ""

            DetalleMazoScreen(
                mazoId = mazoId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("lista_mazos") {
            ListaMazosScreen(
                onBack = {
                    navController.popBackStack()
                },
                onMetaClick = {
                    navController.navigate("tier_list")
                },
                onMazoClick = { mazoId ->
                    navController.navigate("ver_mazo/$mazoId")
                },
                onPerfilClick = { userId ->
                    navController.navigate("perfil/$userId")
                }
            )
        }
        composable("tier_list") {
            TierListScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "ver_mazo/{mazoId}",
            arguments = listOf(navArgument("mazoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mazoId = backStackEntry.arguments?.getString("mazoId") ?: ""

            VerMazoScreen(
                mazoId = mazoId,
                onBack = {
                    navController.popBackStack()
                },
                onPerfilAutor = { autorId->
                    navController.navigate("perfil/$autorId")
                },
                onComentariosClick = {
                    navController.navigate("comentarios/$mazoId")
                }
            )
        }
        composable(
            route = "comentarios/{mazoId}",
            arguments = listOf(navArgument("mazoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mazoId = backStackEntry.arguments?.getString("mazoId") ?: ""

            ComentariosScreen(
                mazoId = mazoId,
                onBack = {
                    navController.popBackStack()
                },
                onPerfilClick = { autorId->
                    navController.navigate("perfil/$autorId")
                }
            )
        }
        composable("perfil") {
            PerfilScreen(
                userId = null,
                onBack = {
                    navController.popBackStack()
                },
                onEditarPerfil = {
                    println("TODO: Editar perfil")
                },
                onVerMazo = { mazoId ->
                    navController.navigate("ver_mazo/$mazoId")
                },
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = "perfil/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            PerfilScreen(
                userId = userId,
                onBack = {
                    navController.popBackStack()
                },
                onEditarPerfil = {
                    println("TODO: Editar perfil")
                },
                onVerMazo = { mazoId ->
                    navController.navigate("ver_mazo/$mazoId")
                },
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
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
                }
            )
        }

    }
}
