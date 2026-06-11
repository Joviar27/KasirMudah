package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.utils.FormatUtil.decimalFormat

@Composable
fun ReceiptItem(
    item: TransactionItemInfo
){
    val formattedAmount = remember(item.itemTotal) {
        "Rp ${item.itemTotal.toString().decimalFormat()},00"
    }

    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            modifier = Modifier.weight(3f),
            text = stringResource(R.string.name_count, item.name, item.count),
            style = KasirMudahTypography.labelMedium
        )
        Spacer(Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(2f),
            text = formattedAmount,
            style = KasirMudahTypography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.End
        )
    }
}
@Preview
@Composable
fun ReceiptItemPrev() {
    ReceiptItem(
        TransactionItemInfo(
            name = "Barang Nomor 15",
            itemTotal = 980000,
            count = 5
        )
    )
}