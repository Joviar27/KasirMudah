package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.White
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.nav.FloatingNavItem
import com.cobasendiri.kasirmudah.nav.Screen
import com.cobasendiri.kasirmudah.ui.theme.Surface

@Composable
fun FloatingNavigationBar(
    items: List<FloatingNavItem<Screen>>,
    currentRoute: NavDestination?,
    onItemClick: (Screen) -> Unit
){
    Row(Modifier
        .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
        .background(White, shape = RoundedCornerShape(24.dp))
        .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach {
            val isSelected = currentRoute?.hasRoute(it.route::class) == true
            FloatingNavigationBarItem(
                icon = painterResource(it.iconRes),
                isSelected = isSelected
            ){
                onItemClick.invoke(it.route)
            }
        }
    }
}

@Composable
private fun FloatingNavigationBarItem(
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
){

    Box(
        modifier = Modifier
            .size(width = 68.dp, height = 58.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = if(isSelected) OnPrimary else Surface)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = icon,
            contentDescription = null,
            tint = if(isSelected) White else OnPrimary
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF8E8E8E
)
@Composable
fun FloatingNavBarPreview(){
    KasirMudahTheme {
        FloatingNavigationBar(
            listOf(
                FloatingNavItem(Screen.Shop, R.drawable.ic_discount),
                FloatingNavItem(Screen.History, R.drawable.ic_history),
                FloatingNavItem(Screen.Profile, R.drawable.ic_profile),
            ),
            null
        ) { }
    }
}

