package com.example.notduo.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notduo.data.RegistrationViewModel
import com.example.notduo.data.SessionManager

@Composable
fun RegistrationScreen(navController: NavController, viewModel: RegistrationViewModel, context: Context) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Text(
                "Register for NotDuo",
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(64.dp))

            TextField(
                label = { Text("First Name") },
                singleLine = true,
                value = viewModel.firstName,
                onValueChange = {viewModel.firstName = it},
                modifier = Modifier.fillMaxWidth(),
                supportingText = {}
            )

            TextField(
                label = { Text("Last Name") },
                singleLine = true,
                value = viewModel.lastName,
                onValueChange = {viewModel.lastName = it},
                modifier = Modifier.fillMaxWidth(),
                supportingText = {}
            )

            TextField(
                label = { Text("Username") },
                singleLine = true,
                value = viewModel.username,
                onValueChange = {viewModel.username = it},
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    if (!viewModel.validUsername) {
                        Text(
                            "Username Already Taken!",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red
                        )
                    }

                }
            )

            TextField(
                label = { Text("Password") },
                singleLine = true,
                value = viewModel.password,
                onValueChange = {viewModel.password = it},
                modifier = Modifier.fillMaxWidth(),
                isError = (!viewModel.validPassword),
                supportingText = {
                    if (!viewModel.validPassword) {
                        Text(
                            "Invalid Password!",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red
                        )
                    }
                }
            )

            TextField(
                label = { Text("Re-enter Password") },
                singleLine = true,
                value = viewModel.verifyPassword,
                onValueChange = {viewModel.verifyPassword = it},
                modifier = Modifier.fillMaxWidth(),
                isError = (!viewModel.validPassword),
                supportingText = {
                    if (!viewModel.validPassword) {
                        Text(
                            "Invalid Password!",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { viewModel.registerUser() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register!")
            }

            if (viewModel.isRegistered) {
                SessionManager.startUserSession(context, viewModel.username)

                navController.navigate("mainApp") {
                    popUpTo("register") { inclusive = true }
                }
            }
        }
    }
}