package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.Negative
import com.cobasendiri.kasirmudah.ui.theme.Secondary
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun FloatingAction(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onDone: () -> Unit
) {
    Row(modifier.width(280.dp)
        .shadow(elevation = 3.dp, shape = RoundedCornerShape(32.dp))
    ) {
        Row(Modifier.clip(RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp))
            .clickable(onClick = onDelete)
            .background(Negative)
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(R.drawable.ic_delete_white_round),
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(5f),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.delete),
                style = KasirMudahTypography.bodyMedium
                    .copy(fontWeight = FontWeight.SemiBold, color = White)
            )
        }
        Row(Modifier.clip(RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp))
            .clickable(onClick = onDone)
            .background(Secondary)
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(5f),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.done),
                style = KasirMudahTypography.bodyMedium
                    .copy(fontWeight = FontWeight.SemiBold)
            )
            Image(
                modifier = Modifier.weight(1f)
                    .size(17.dp),
                painter = painterResource(R.drawable.ic_right_arrow_white_round),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun FloatingActionPrev() {
    FloatingAction(
        onDelete = {}
    ){}
}