package com.cobasendiri.kasirmudah.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.nav.FloatingNavItem
import com.cobasendiri.kasirmudah.nav.Screen
import com.cobasendiri.kasirmudah.ui.history.HistoryScreen
import com.cobasendiri.kasirmudah.ui.profile.ProfileScreen
import com.cobasendiri.kasirmudah.ui.shop.ShopScreen
import com.cobasendiri.kasirmudah.ui.component.FloatingNavigationBar
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme

@Composable
fun MainContent(){
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    val mainNavItems = listOf<FloatingNavItem<Screen>>(
        FloatingNavItem(Screen.Shop, R.drawable.ic_discount),
        FloatingNavItem(Screen.History, R.drawable.ic_history),
        FloatingNavItem(Screen.Profile, R.drawable.ic_profile),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(Modifier.fillMaxWidth()
                .padding(bottom = 24.dp),
                contentAlignment = Alignment.BottomCenter
            ){
                FloatingNavigationBar(
                    mainNavItems,
                    currentRoute
                ) { route ->
                    navController.navigate(route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Shop,
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding()
            )
        ){
            composable<Screen.Shop>{
                ShopScreen(innerPadding)
            }
            composable<Screen.History>{
                HistoryScreen()
            }
            composable<Screen.Profile>{
                ProfileScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    KasirMudahTheme {
        MainContent()
    }
}
