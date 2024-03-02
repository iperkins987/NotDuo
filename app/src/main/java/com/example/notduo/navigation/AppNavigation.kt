package com.example.notduo.navigation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notduo.data.MainAppViewModel
import com.example.notduo.data.RegistrationViewModel
import com.example.notduo.data.SessionManager
import com.example.notduo.screens.MainAppScreen
import com.example.notduo.screens.RegistrationScreen

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    var sessionIsActive by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    // Check if we are logged in
    LaunchedEffect(Unit) {
        sessionIsActive = SessionManager.isSessionActive(context)
        isLoading = false
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val startDestination = if (sessionIsActive) "mainApp" else "registration"

        NavHost(navController = navController, startDestination = startDestination) {
            composable("registration") {
                val registrationViewModel = viewModel<RegistrationViewModel>()
                RegistrationScreen(navController, registrationViewModel, context)
            }

            composable("mainApp") {
                val mainAppViewModel = viewModel<MainAppViewModel>()
                MainAppScreen(mainAppViewModel)
            }
        }
    }
}