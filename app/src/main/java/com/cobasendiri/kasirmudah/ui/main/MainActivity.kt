package com.cobasendiri.kasirmudah.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KasirMudahTheme {
                MainContent()
            }
        }
    }
}
