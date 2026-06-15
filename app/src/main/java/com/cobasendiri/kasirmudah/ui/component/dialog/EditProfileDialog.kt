package com.cobasendiri.kasirmudah.ui.component.dialog

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.model.ShopProfile
import com.cobasendiri.kasirmudah.ui.component.textfield.InputField
import com.cobasendiri.kasirmudah.ui.component.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudah.ui.component.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun EditProfileDialog(
    shopProfile: ShopProfile,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onSave: (ShopProfile) -> Unit
) {

    var shopNameDraft by remember(shopProfile.shopName) {
        mutableStateOf((shopProfile.shopName))
    }

    var shopImageDraft by remember(shopProfile.shopImage) {
        mutableStateOf((shopProfile.shopImage))
    }

    val isNextButtonEnabled = remember(shopNameDraft){
        shopNameDraft.let {
            it.isNotBlank() && it.firstOrNull()?.isWhitespace() == false
        }
    }

    val context = LocalContext.current
    val applicationContext = context.applicationContext

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        shopImageDraft = uri?.let {
            val contentResolver = applicationContext.contentResolver
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION

            val result = runCatching {
                contentResolver.takePersistableUriPermission(it, takeFlags)
            }
            if(result.isSuccess) it.toString() else ""
        } ?: ""
    }

    BaseDialog(
        onDismiss = onDismiss
    ) {
        Column(Modifier.background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.edit_shop_profile),
                style = KasirMudahTypography.headlineSmall
            )
            Spacer(Modifier.height(16.dp))
            Box(contentAlignment = Alignment.Center){
                AsyncImage(
                    modifier = Modifier.size(120.dp)
                        .clip(CircleShape)
                        .background(Surface),
                    model = shopImageDraft,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    fallback = painterResource(R.drawable.ic_person_padded),
                    error = painterResource(R.drawable.ic_person_padded),
                )
                Image(
                    modifier = Modifier.size(34.dp)
                        .clip(CircleShape)
                        .background(OnPrimary)
                        .clickable{
                            imageLauncher.launch("image/*")
                        }
                        .padding(8.dp)
                        .align(Alignment.TopEnd),
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null
                )
            }
            Spacer(Modifier.height(24.dp))
            InputField(
                label = stringResource(R.string.shop_name),
                value = shopNameDraft,
                maxCharacter = 35
            ) {
                shopNameDraft = it
            }
            Spacer(Modifier.height(24.dp))
            Row {
                RoundedOutlinedButton(
                    modifier = Modifier.width(140.dp),
                    text = stringResource(R.string.cancel)
                ){
                    onCancel.invoke()
                }
                Spacer(Modifier.width(8.dp))
                RoundedPrimaryButton(
                    modifier = Modifier.width(140.dp),
                    text = stringResource(R.string.save),
                    isEnabled = isNextButtonEnabled
                ) {
                    onSave.invoke(ShopProfile(shopNameDraft,shopImageDraft))
                }
            }
        }
    }
}

@Preview
@Composable
fun EditProfileDialogPrev() {
    EditProfileDialog(
        ShopProfile(
            "Toko Madura A",
            ""
        ),
        {null},{},{}
    )
}