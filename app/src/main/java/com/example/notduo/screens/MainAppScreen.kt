package com.example.notduo.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.notduo.data.MainAppViewModel

@Composable
fun MainAppScreen(viewModel: MainAppViewModel) {

    if (viewModel.isAuth) {
        Text("Auth Token is ${viewModel.authToken}")
        Button(
            onClick = { viewModel.sendResponse(viewModel.authToken) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("AUTHENTICATE")
        }
    } else {
        Text("No New Auth Tokens")
    }
}