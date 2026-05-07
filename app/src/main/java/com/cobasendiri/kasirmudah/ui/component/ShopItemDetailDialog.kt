package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.PopupProperties
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.model.Shop
import com.cobasendiri.kasirmudah.model.ShopDraft
import com.cobasendiri.kasirmudah.ui.component.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudah.ui.component.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun ShopItemDetailDialog(
    shop: Shop? = null,
    onDismiss: () -> Unit,
    onSave: (ShopDraft) -> Unit
) {
    var shopDraft by remember { mutableStateOf(
        ShopDraft(
            id = shop?.id ?: "",
            name = shop?.name ?: "",
            price = shop?.price?.toString() ?: "",
            colorCode = shop?.colorCode ?: Tertiary
        )
    )}

    var showColorCodePopup by remember { mutableStateOf(false) }

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismiss
    ) {
        Surface(Modifier.fillMaxWidth()
            .padding(16.dp)
            .background(White, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Column(Modifier.background(White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ColorCodeDetail(shopDraft.colorCode) {
                        showColorCodePopup = true
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(
                            if(shopDraft.id.isEmpty()) R.string.add_item
                            else R.string.edit_item
                        ),
                        style = KasirMudahTypography.headlineSmall
                    )
                    if(showColorCodePopup){
                        ColorCodePopup(
                            alignment = Alignment.BottomStart,
                            offset = IntOffset(0,110),
                            properties = PopupProperties(focusable = true)
                        ){ newColor ->
                            showColorCodePopup = false
                            newColor?.let {
                                shopDraft = shopDraft.copy(colorCode = it)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
                InputField(
                    label = stringResource(R.string.item_name),
                    initialValue = shopDraft.name,
                    maxCharacter = 35
                ) {
                    shopDraft = shopDraft.copy(name = it)
                }
                Spacer(Modifier.height(16.dp))
                InputField(
                    label = stringResource(R.string.item_price),
                    initialValue = shopDraft.price,
                    currencyMode = true,
                    maxCharacter = 15
                ) {
                    shopDraft = shopDraft.copy(price = it)
                }
                Spacer(Modifier.height(24.dp))
                Row {
                    RoundedOutlinedButton(
                        modifier = Modifier.width(140.dp),
                        text = stringResource(R.string.cancel),
                        onClick = onDismiss
                    )
                    Spacer(Modifier.width(8.dp))
                    RoundedPrimaryButton(
                        modifier = Modifier.width(140.dp),
                        text = stringResource(R.string.save)
                    ) {
                        onSave.invoke(shopDraft)
                        onDismiss.invoke()
                    }
                }
            }
        }
    }
}

@Composable
fun ColorCodeDetail(
    color: Color,
    onEditClick: () -> Unit
){
    Box(Modifier.size(40.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(color)
        .clickable(onClick = onEditClick)
        .padding(12.dp),
        contentAlignment = Alignment.Center
    ){
        Box(Modifier.size(16.dp)
            .clip(CircleShape)
            .background(OnPrimary, CircleShape),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun ItemDetailPrev() {
    Column(Modifier.fillMaxWidth()) {
        ShopItemDetailDialog(
            shop = Shop(
                id = "1",
                name = "Barang Pertama",
                price = 15000L,
                colorCode = Tertiary
            ),
            onDismiss = {}
        ){}
    }
}

@Preview
@Composable
fun ItemDetailPrev2() {
    ShopItemDetailDialog(
        onDismiss = {}
    ){}
}