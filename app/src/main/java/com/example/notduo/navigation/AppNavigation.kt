package com.example.notduo.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notduo.data.RegistrationViewModel
import com.example.notduo.screens.MainAppScreen
import com.example.notduo.screens.RegistrationScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "registration") {
        composable("registration") {
            val registrationViewModel = viewModel<RegistrationViewModel>()
            RegistrationScreen(navController, registrationViewModel)
        }
        composable("mainApp") { MainAppScreen() }
    }
}