package com.cobasendiri.kasirmudah.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cobasendiri.kasirmudah.nav.Screen
import com.cobasendiri.kasirmudah.ui.receipt.detail.ReceiptDetailScreen
import com.cobasendiri.kasirmudah.ui.receipt.draft.ReceiptDraftScreen
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val rootNavController = rememberNavController()

            KasirMudahTheme {
                NavHost(
                    navController = rootNavController,
                    startDestination = Screen.MainTabs
                ){
                    composable<Screen.MainTabs> {
                        MainTabScreen(rootNavController)
                    }
                    composable<Screen.ReceiptDraft> {
                        ReceiptDraftScreen(
                            onNavigateBack = {
                                rootNavController.popBackStack()
                            }
                        )
                    }
                    composable<Screen.ReceiptDetail> { backStackEntry ->
                        val args = backStackEntry.toRoute<Screen.ReceiptDetail>()
                        ReceiptDetailScreen(args.transactionId){
                            rootNavController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}
