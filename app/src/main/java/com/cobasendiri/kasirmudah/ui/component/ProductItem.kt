package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.ui.component.popup.ActionPopup
import com.cobasendiri.kasirmudah.ui.component.popup.ColorCodePopup
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.White
import com.cobasendiri.kasirmudah.ui.utils.FormatUtil.decimalFormat

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    state: ProductInfo,
    onEdit: (Product) -> Unit,
    onDelete: (String) -> Unit,
    onColorCodeUpdate: (Long) -> Unit,
    onItemIncrease: () -> Unit,
    onItemDecrease: () -> Unit
) {

    val formattedPrice = remember(state.product.price) {
        "Rp ${state.product.price.toString().decimalFormat()},00"
    }

    val selectedColorCode = remember(state.product.colorCode) {
        Color(value = state.product.colorCode.toULong())
    }

    var showColorCodePopup by remember { mutableStateOf(false) }
    var showActionPopup by remember { mutableStateOf(false) }

    Row(modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(24.dp))
        .background(White)
        .clickable{
            showActionPopup = true
        }
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box{
            ColorCode(selectedColorCode){
                showColorCodePopup = true
            }
            if(showColorCodePopup){
                ColorCodePopup(
                    alignment = Alignment.TopStart,
                    offset = IntOffset(0, -110),
                    properties = PopupProperties(focusable = true)
                ) { newColor ->
                    showColorCodePopup = false
                    newColor?.let {
                        onColorCodeUpdate.invoke(it.toColorLong())
                    }
                }
            }
        }

        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                state.product.name,
                style = KasirMudahTypography.titleMedium
                    .copy(fontWeight = FontWeight.Bold)
            )
            Text(
                formattedPrice,
                style = KasirMudahTypography.bodyMedium
            )
        }
        Counter(
            count = state.count,
            onIncrease = {
                onItemIncrease.invoke()
            },
            onDecrease = {
                onItemDecrease.invoke()
            }
        )
        if(showActionPopup){
            ActionPopup(
                firsItem = Pair(
                    painterResource(R.drawable.ic_edit_22),
                    stringResource(R.string.edit)
                ),
                secondItem = Pair(
                    painterResource(R.drawable.ic_delete_22),
                    stringResource(R.string.delete)
                ),
                alignment = Alignment.BottomEnd,
                offset = IntOffset(30, 135),
                properties = PopupProperties(focusable = true),
                onDismiss = {
                    showActionPopup = false
                },
                onFirstItemClick = {
                    onEdit.invoke(state.product)
                },
                onSecondItemClick = {
                    onDelete.invoke(state.product.id)
                }
            )
        }
    }
}

@Composable
fun Counter(
    count: Int = 0,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Box(Modifier
            .clip(CircleShape)
            .background(Color.Transparent)
            .border(1.dp, OnPrimaryVariant, CircleShape)
            .padding(1.dp)
            .clickable {
                if (count > 0) {
                    onDecrease.invoke()
                }
            }
        ){
            Image(
                painterResource(R.drawable.ic_minus_13),
                contentDescription = null
            )
        }
        Box(Modifier
            .width(24.dp)
            .background(Surface, RoundedCornerShape(4.dp))
            .padding(2.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                count.toString(),
                style = KasirMudahTypography.bodyMedium
            )
        }
        Box(Modifier
            .clip(CircleShape)
            .background(Color.Transparent)
            .border(1.dp, OnPrimaryVariant, CircleShape)
            .padding(1.dp)
            .clickable {
                if(count<99){
                    onIncrease.invoke()
                }
            }
        ){
            Image(
                painterResource(R.drawable.ic_plus_13),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ColorCode(
    color: Color,
    onEditClick: () -> Unit
){
    Box(Modifier
        .size(64.dp)
        .background(color, RoundedCornerShape(16.dp))
        .padding(8.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Box(Modifier
            .clip(CircleShape)
            .background(OnPrimary, CircleShape)
            .size(16.dp)
            .clickable(onClick = onEditClick),
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
fun ShopItemPrev() {
    val product = Product(
        id = "1",
        name = "Nama Item 1",
        price = 15000,
        colorCode = Tertiary.toColorLong()
    )
    Box(Modifier.padding(16.dp)){
        ProductItem(
            state = ProductInfo(
                product = product,
                count = 2
            ),
            onEdit = {},
            onDelete = {},
            onColorCodeUpdate = {},
            onItemIncrease = {}
        ){}
    }
}