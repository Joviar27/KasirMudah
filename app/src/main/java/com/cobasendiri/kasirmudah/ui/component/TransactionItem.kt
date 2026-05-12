package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.White
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.component.popup.ActionPopup
import com.cobasendiri.kasirmudah.ui.history.TransactionItemState

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    state: TransactionItemState,
    onBookmark: (String) -> Unit,
    onDelete: (String) -> Unit
) {

    var showActionPopup by remember { mutableStateOf(false) }

    Row(modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(24.dp))
        .background(White)
        .clickable{
            showActionPopup = true
        }
        .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = state.name,
                style = KasirMudahTypography.titleMedium
                    .copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                state.createdAt,
                style = KasirMudahTypography.bodyMedium
            )
        }
        if(state.isBookmarked){
            Image(
                painter = painterResource(R.drawable.ic_bookmarked_27),
                contentDescription = null
            )
        }
        if(showActionPopup){
            ActionPopup(
                firsItem = Pair(
                    painterResource(R.drawable.ic_bookmark_22),
                    stringResource(if(state.isBookmarked) R.string.cancel else R.string.bookmark)
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
                    onBookmark.invoke(state.id)
                },
                onSecondItemClick = {
                    onDelete.invoke(state.id)
                }
            )
        }
    }
}

@Preview
@Composable
fun TransactionItemPrev() {
    TransactionItem(
        state = TransactionItemState(
            id = "uefwofgew",
            name = "Transaksi 347295793wegwyf",
            createdAt = "12 Agustus 2026 - 12:53:01",
            isBookmarked = true
        ),
        onDelete = {},
        onBookmark = {}
    )
}