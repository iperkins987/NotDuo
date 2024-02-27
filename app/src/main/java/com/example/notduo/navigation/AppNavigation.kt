package com.example.notduo.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notduo.data.RegistrationViewModel
import com.example.notduo.data.SessionManager
import com.example.notduo.screens.MainAppScreen
import com.example.notduo.screens.RegistrationScreen

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    var sessionIsActive by remember { mutableStateOf(false) }

    // Check if we are logged in
    LaunchedEffect(Unit) {
        sessionIsActive = SessionManager.isSessionActive(context)
    }

    val startDestination = if (sessionIsActive) "mainApp" else "registration"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("registration") {
            val registrationViewModel = viewModel<RegistrationViewModel>()
            RegistrationScreen(navController, registrationViewModel, context)
        }
        composable("mainApp") { MainAppScreen() }
    }
}