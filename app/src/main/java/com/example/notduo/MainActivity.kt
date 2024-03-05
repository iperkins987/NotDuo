package com.example.notduo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.example.notduo.navigation.AppNavigation
import com.example.notduo.ui.theme.NotDuoTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotDuoTheme {
                AppNavigation(this)
            }
        }
    }
}