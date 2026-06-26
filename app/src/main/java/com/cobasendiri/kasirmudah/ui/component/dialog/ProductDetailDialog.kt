package com.cobasendiri.kasirmudah.ui.component.dialog

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.ui.component.inputfield.InputField
import com.cobasendiri.kasirmudah.ui.component.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudah.ui.component.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudah.ui.component.popup.ColorCodePopup
import com.cobasendiri.kasirmudah.ui.component.inputfield.CurrencyInputField
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun ProductDetailDialog(
    product: Product? = null,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onSave: (ProductDraft) -> Unit
) {
    var productDraft by remember { mutableStateOf(
        ProductDraft(
            id = product?.id ?: "",
            name = product?.name ?: "",
            price = product?.price?.toString() ?: "",
            colorCode = product?.colorCode ?: Tertiary.toColorLong()
        )
    )}

    val selectedColorCode = remember(productDraft.colorCode) {
        Color(value = productDraft.colorCode.toULong())
    }

    var showColorCodePopup by remember { mutableStateOf(false) }

    val isNameValid = remember(productDraft.name){
        productDraft.let {
            it.name.isNotBlank() && it.name.firstOrNull()?.isWhitespace() == false
        }
    }

    val isPriceValid = remember(productDraft.price){
        productDraft.let {
            it.price.isNotBlank() && !it.price.contains(" ")
                    && !it.price.all { it == '0' }
        }
    }

    BaseDialog(
        onDismiss = onDismiss
    ) {
        Column(Modifier.background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ColorCodeDetail(selectedColorCode) {
                    showColorCodePopup = true
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = stringResource(
                        if(productDraft.id.isEmpty()) R.string.add_item
                        else R.string.edit_item
                    ),
                    style = KasirMudahTypography.headlineSmall
                )
                if(showColorCodePopup){
                    ColorCodePopup(
                        alignment = Alignment.BottomStart,
                        offset = IntOffset(0, 110),
                        properties = PopupProperties(focusable = true)
                    ) { newColor ->
                        showColorCodePopup = false
                        newColor?.let {
                            productDraft = productDraft.copy(colorCode = it.toColorLong())
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            InputField(
                label = stringResource(R.string.item_name),
                value = productDraft.name,
                maxCharacter = 35
            ) {
                productDraft = productDraft.copy(name = it)
            }
            Spacer(Modifier.height(16.dp))
            CurrencyInputField(
                label = stringResource(R.string.item_price),
                value = productDraft.price,
                maxCharacter = 15
            ) {
                productDraft = productDraft.copy(price = it)
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
                    isEnabled = isNameValid && isPriceValid,
                    modifier = Modifier.width(140.dp),
                    text = stringResource(R.string.save)
                ) {
                    onSave.invoke(productDraft)
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
    Box(Modifier
        .size(40.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(color)
        .clickable(onClick = onEditClick)
        .padding(12.dp),
        contentAlignment = Alignment.Center
    ){
        Box(Modifier
            .size(16.dp)
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
        ProductDetailDialog(
            product = Product(
                id = "1",
                name = "Barang Pertama",
                price = 15000L,
                colorCode = Tertiary.toColorLong()
            ),
            onCancel = {},
            onDismiss = {},
            onSave = {}
        )
    }
}

@Preview
@Composable
fun ItemDetailPrev2() {
    ProductDetailDialog(
        onDismiss = {},
        onCancel = {}
    ){}
}