package com.example.notduo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.example.notduo.data.MainAppViewModel

@Composable
fun MainAppScreen(viewModel: MainAppViewModel, activity: FragmentActivity) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            if (viewModel.isAuth) {
                Button(
                    onClick = { viewModel.authenticate(activity) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Authenticate")
                }
            } else {
                Text("Waiting for an authentication request")
            }
        }
    }
}