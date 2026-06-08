package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.component.popup.ActionPopup
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptTopBar(
    modifier: Modifier = Modifier,
    statusBarHeight: Dp = 0.dp,
    showMenuIcon: Boolean = false,
    isBookmarked: Boolean = false,
    onUpdateBookmark: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onNavigateBack: () -> Unit
) {

    var showActionPopup by remember { mutableStateOf(false) }

    Row(modifier.fillMaxWidth()
        .height(64.dp + statusBarHeight)
        .background(Primary)
        .padding(horizontal = 16.dp)
        .padding(top = 16.dp+statusBarHeight, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.clip(CircleShape)
                .clickable{
                onNavigateBack.invoke()
            },
            painter = painterResource(R.drawable.ic_arrow_left_32),
            contentDescription = null
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.receipt_title),
            textAlign = TextAlign.Center,
            style = KasirMudahTypography.titleMedium
        )
        if(showMenuIcon){
            Image(
                modifier = Modifier.size(32.dp)
                    .clip(CircleShape).clickable{ 
                        showActionPopup = true
                    },
                painter = painterResource(R.drawable.ic_more_40),
                contentDescription = null
            )
        }else{
            Spacer(Modifier.width(32.dp))
        }

        if(showActionPopup){
            ActionPopup(
                firsItem = Pair(
                    painterResource(R.drawable.ic_bookmark_22),
                    stringResource(if(isBookmarked) R.string.cancel else R.string.bookmark)
                ),
                secondItem = Pair(
                    painterResource(R.drawable.ic_delete_22),
                    stringResource(R.string.delete)
                ),
                alignment = Alignment.BottomEnd,
                offset = IntOffset(0, 150),
                properties = PopupProperties(focusable = true),
                onDismiss = {
                    showActionPopup = false
                },
                onFirstItemClick = {
                    onUpdateBookmark?.invoke()
                },
                onSecondItemClick = {
                    onDelete?.invoke()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPrev() {
    ReceiptTopBar(
        onUpdateBookmark = {},
        onDelete = {},
        showMenuIcon = true
    ){}
}