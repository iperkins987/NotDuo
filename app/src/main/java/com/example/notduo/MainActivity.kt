package com.example.notduo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.notduo.navigation.AppNavigation
import com.example.notduo.ui.theme.NotDuoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotDuoTheme {
                AppNavigation()
            }
        }
    }
}