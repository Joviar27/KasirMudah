package com.cobasendiri.kasirmudah.ui.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.model.ShopProfile
import com.cobasendiri.kasirmudah.ui.ViewModelFactory
import com.cobasendiri.kasirmudah.ui.component.ProfileMenuItem
import com.cobasendiri.kasirmudah.ui.component.dialog.EditProfileDialog
import com.cobasendiri.kasirmudah.ui.component.dialog.InformationConfirmDialog
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun ProfileScreen(
    innerPadding: PaddingValues
){
    val context = LocalContext.current
    val appContext = context.applicationContext

    val viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory.getInstance(appContext)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.updateSelectedImage(uri?.toString() ?: "")
    }

    ProfileScreenContent(
        innerPadding,
        state
    ){ event ->
        when(event){
            is ProfileEvent.OnShowEditProfileDialog ->{
                viewModel.showEditProfileDialog(event.shopProfile)
            }
            is ProfileEvent.OnDismissEditProfileDialog ->{
                viewModel.dismissEditProfileDialog()
            }
            is ProfileEvent.OnShowUnavailableDialog ->{
                viewModel.showUnavailableDialog()
            }
            is ProfileEvent.OnDismissUnavailableDialog ->{
                viewModel.dismissUnavailableDialog()
            }
            is ProfileEvent.OnLaunchImagePicker ->{
                imageLauncher.launch("image/*")
            }
            is ProfileEvent.OnEditProfile ->{
                viewModel.saveShopProfile(event.newShopProfile)
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
            Spacer(Modifier.height(topPadding))
            Image(
                modifier = Modifier.size(180.dp,25.dp),
                painter = painterResource(R.drawable.ic_kasirmudah),
                contentDescription = null,
                alignment = Alignment.CenterStart
            )
            Spacer(Modifier.height(24.dp))
            AsyncImage(
                modifier = Modifier.size(130.dp)
                    .clip(CircleShape)
                    .background(White),
                model = state.shopProfile.shopImage.takeIf { it.isNotBlank() },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                fallback = painterResource(R.drawable.ic_person_padded),
                error = painterResource(R.drawable.ic_person_padded)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = state.shopProfile.shopName.takeIf { it.isNotBlank() }
                    ?: stringResource(R.string.default_shop_name),
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
        if(state.showEditProfileDialog != null){
            val dismissEditDialogEvent = ProfileEvent.OnDismissEditProfileDialog
            EditProfileDialog(
                state.showEditProfileDialog,
                onPickImage = {
                    event.invoke(ProfileEvent.OnLaunchImagePicker)
                },
                onDismiss = { event.invoke(dismissEditDialogEvent) },
                onCancel = { event.invoke(dismissEditDialogEvent) },
                onSave = {
                    event.invoke(ProfileEvent.OnEditProfile(it))
                }
            )
        }
        if(state.showUnavailableDialog){
            val dismissUnavailableDialog = ProfileEvent.OnDismissUnavailableDialog
            InformationConfirmDialog(
                title = stringResource(R.string.unavailable_title),
                body = stringResource(R.string.unavailable_body),
                confirmButton = stringResource(R.string.close),
                onDismiss = { event.invoke(dismissUnavailableDialog) },
                onConfirm = { event.invoke(dismissUnavailableDialog) }
            )
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
                    ""
                ),
                showEditProfileDialog = null,
                showUnavailableDialog = false
            )
        ){}
    }
}