package com.cobasendiri.kasirmudah.ui.receipt.detail
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudah.ui.component.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudah.ui.component.dialog.NegativeConfirmDialog
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.ui.utils.FormatUtil.dateFormat
import com.cobasendiri.kasirmudah.ui.utils.FormatUtil.decimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptDetailContent(
    innerPadding: PaddingValues,
    state: ReceiptDetailState,
    event: (ReceiptDetailEvent) -> Unit
){
    val formattedTotal = remember(state.totalTransaction) {
        "Rp ${state.totalTransaction.toString().decimalFormat()},00"
    }

    val formattedDate = remember(state.transactionCreatedAt) {
        state.transactionCreatedAt.dateFormat()
    }

    val scrollState = rememberScrollState()

    Box(Modifier.fillMaxSize()
        .background(Surface)
        .padding(innerPadding)
    ){
        Column(Modifier.padding(horizontal = 24.dp, vertical = 16.dp)){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(8.dp))
                Image(
                    modifier = Modifier.size(180.dp,25.dp),
                    painter = painterResource(R.drawable.ic_kasirmudah),
                    contentDescription = null,
                    alignment = Alignment.CenterStart
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = state.shopName,
                    style = KasirMudahTypography.titleLarge
                )
                Text(
                    text = formattedDate,
                    style = KasirMudahTypography.bodyLarge
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.transaction_id, state.transactionId),
                    style = KasirMudahTypography.labelSmall
                )
                Spacer(Modifier.height(36.dp))
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.item_name),
                        style = KasirMudahTypography.bodyMedium
                    )
                    Text(
                        text = stringResource(R.string.price),
                        style = KasirMudahTypography.bodyMedium
                    )
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = OnPrimary
                )
                Spacer(Modifier.height(10.dp))
                state.transactionShopItems.forEach { item ->
                    Spacer(Modifier.height(6.dp))
                    ReceiptItem(item)
                    Spacer(Modifier.height(6.dp))
                }
                Spacer(Modifier.height(36.dp))
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.total),
                        style = KasirMudahTypography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = formattedTotal,
                        style = KasirMudahTypography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = OnPrimary
                )
            }
            Spacer(Modifier.height(16.dp))
            RoundedPrimaryButton(
                text = stringResource(R.string.download_receipt)
            ) {
                event.invoke(ReceiptDetailEvent.OnDownload)
            }
        }
        if(state.showConfirmDeleteDialog != null){
            val dismissEvent = ReceiptDetailEvent.OnDismissConfirmDeleteDialog
            val transactionId = state.showConfirmDeleteDialog
            NegativeConfirmDialog(
                title = stringResource(R.string.delete_transaction_title),
                body = stringResource(R.string.delete_transaction_body),
                cancelButton = stringResource(R.string.cancel),
                confirmButton = stringResource(R.string.delete),
                onDismiss = { event.invoke(dismissEvent) },
                onCancel = { event.invoke(dismissEvent) },
                onConfirm = {
                    event.invoke(ReceiptDetailEvent.OnDelete(transactionId))
                }
            )
        }
    }
}

@Composable
fun ReceiptItem(
    item: TransactionItemInfo
){
    val formattedAmount = remember(item.itemTotal) {
        "Rp ${item.itemTotal.toString().decimalFormat()},00"
    }

    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.name_count, item.name, item.count),
            style = KasirMudahTypography.labelMedium
        )
        Text(
            text = formattedAmount,
            style = KasirMudahTypography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ReceiptContentPrev(){
    ReceiptDetailContent(
        PaddingValues(0.dp),
        ReceiptDetailState(
            shopName = "Toko Madura A",
            transactionCreatedAt = 1755388800,
            transactionId = "4shisefhw48t4",
            transactionShopItems = MutableList(6) {
                TransactionItemInfo(
                    name = "Barang Nomor $it",
                    itemTotal = 980000,
                    count = 5
                )
            },
            totalTransaction = 1500225
        )
    ){}
}