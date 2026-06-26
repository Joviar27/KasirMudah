package com.cobasendiri.kasirmudah.ui.component.popup 
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun ActionPopup(
    firsItem: Pair<Painter, String>,
    secondItem: Pair<Painter, String>,
    alignment: Alignment,
    offset: IntOffset,
    properties: PopupProperties,
    onFirstItemClick: () -> Unit,
    onSecondItemClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Popup(
        alignment = alignment,
        offset = offset,
        onDismissRequest = {
            onDismiss.invoke()
        },
        properties = properties
    ){
        ActionSelect(
            firsItem,
            secondItem,
            onFirstItemClick = {
                onFirstItemClick.invoke()
                onDismiss.invoke()
            },
            onSecondItemClick = {
                onSecondItemClick.invoke()
                onDismiss.invoke()
            }
        )
    }
}

@Composable
fun ActionSelect(
    firsItem: Pair<Painter, String>,
    secondItem: Pair<Painter, String>,
    onFirstItemClick: () -> Unit,
    onSecondItemClick: () -> Unit
) {
    val roundedShape = remember {
        RoundedCornerShape(
            topStart = 8.dp,
            bottomStart = 8.dp,
            bottomEnd = 8.dp
        )
    }
    Column(
        Modifier.width(105.dp)
            .shadow(elevation = 3.dp, shape = roundedShape)
            .background(White, roundedShape)
    ) {
        Row(Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 8.dp))
            .clickable(onClick = onFirstItemClick)
            .padding(horizontal = 12.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = firsItem.first,
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = firsItem.second,
                style = KasirMudahTypography.bodyMedium
            )
        }
        HorizontalDivider(
            Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            thickness = 0.3.dp,
            color = OnPrimaryVariant
        )
        Row(Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(
                bottomStart = 8.dp,
                bottomEnd = 8.dp
            ))
            .clickable(onClick = onSecondItemClick)
            .padding(horizontal = 12.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = secondItem.first,
                contentDescription = null
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = secondItem.second,
                style = KasirMudahTypography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun ActionPopupPrev(){
    ActionSelect(
        firsItem = Pair(
            painterResource(R.drawable.ic_bookmark_22),
            stringResource(R.string.bookmark)
        ),
        secondItem = Pair(
            painterResource(R.drawable.ic_delete_22),
            stringResource(R.string.delete)
        ),
        {},{}
    )
}