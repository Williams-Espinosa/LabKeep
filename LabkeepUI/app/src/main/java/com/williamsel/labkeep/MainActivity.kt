package com.williamsel.labkeep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.williamsel.labkeep.core.navigation.NavigationWrapper
import com.williamsel.labkeep.features.login.di.LoginProvider
import com.williamsel.labkeep.ui.theme.LabkeepTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginProvider = LoginProvider()
        enableEdgeToEdge()
        setContent {
            LabkeepTheme {
                NavigationWrapper()
            }
        }
    }
}

