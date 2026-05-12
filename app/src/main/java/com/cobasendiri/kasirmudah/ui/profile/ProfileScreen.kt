package com.cobasendiri.kasirmudah.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.model.ShopProfile
import com.cobasendiri.kasirmudah.ui.component.ProfileMenuItem
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues
){

    val dummyState by remember { mutableStateOf(
        ProfileState(
            shopProfile = ShopProfile(
                shopName = "Toko Madura A",
                shopImage = "url to load image"
            ),
            showEditProfileDialog = null,
            showUnavailableDialog = false
        )
    ) }

    ProfileScreenContent(
        innerPadding,
        dummyState
    ){ event ->
        when(event){
            is ProfileEvent.OnShowEditProfileDialog ->{

            }
            is ProfileEvent.OnDismissEditProfileDialog ->{

            }
            is ProfileEvent.OnShowUnavailableDialog ->{

            }
            is ProfileEvent.OnDismissUnavailableDialog ->{

            }
            is ProfileEvent.OnEditProfile ->{

            }
        }
    }
}

@Composable
fun ProfileScreenContent(
    innerPadding: PaddingValues,
    state: ProfileState,
    event: (ProfileEvent) -> Unit
){
    val topPadding = remember(innerPadding){
        innerPadding.calculateTopPadding()
    }

    Box(Modifier
        .fillMaxSize()
        .background(Surface)
    ) {
        //Top decoration view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topPadding + 250.dp)
                .drawBehind {
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(Primary, Color.Transparent)
                        ),
                        size = Size(size.width, (topPadding + 250.dp).toPx())
                    )
                }
        )

        Column(Modifier.fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(180.dp,25.dp),
                painter = painterResource(R.drawable.ic_kasirmudah),
                contentDescription = null,
                alignment = Alignment.CenterStart
            )
            Spacer(Modifier.height(24.dp))
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(White)
                    .padding(24.dp),
                painter = painterResource(R.drawable.ic_person_85),
                contentDescription = null
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = state.shopProfile.shopName,
                style = KasirMudahTypography.titleLarge
            )
            Spacer(Modifier.height(24.dp))

            ProfileMenuItem(
                startIcon = painterResource(R.drawable.ic_profile),
                name = stringResource(R.string.menu_edit_profile)
            ) {
                event.invoke(ProfileEvent.OnShowEditProfileDialog(state.shopProfile))
            }
            Spacer(Modifier.height(8.dp))
            ProfileMenuItem(
                startIcon = painterResource(R.drawable.ic_about_us_24),
                name = stringResource(R.string.menu_about_us)
            ) {
                event.invoke(ProfileEvent.OnShowUnavailableDialog)
            }
            Spacer(Modifier.height(16.dp))
            ProfileMenuItem(
                startIcon = painterResource(R.drawable.ic_settings_24),
                name = stringResource(R.string.menu_setting)
            ) {
                event.invoke(ProfileEvent.OnShowUnavailableDialog)
            }
            Spacer(Modifier.height(8.dp))
            ProfileMenuItem(
                startIcon = painterResource(R.drawable.ic_cloud_upload_24),
                name = stringResource(R.string.menu_sync_data)
            ) {
                event.invoke(ProfileEvent.OnShowUnavailableDialog)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPrev(){
    KasirMudahTheme {
        ProfileScreenContent(
            innerPadding = PaddingValues(bottom = 60.dp),
            state = ProfileState(
                ShopProfile(
                    "Toko Madura A",
                    "url to load image"
                ),
                showEditProfileDialog = null,
                showUnavailableDialog = false
            )
        ){}
    }
}