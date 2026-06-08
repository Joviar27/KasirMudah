package com.cobasendiri.kasirmudah.ui.receipt 
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.cobasendiri.kasirmudah.domain.model.ReceiptItem
import com.cobasendiri.kasirmudah.ui.component.ReceiptTopBar
import com.cobasendiri.kasirmudah.ui.component.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudah.ui.component.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.Surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptContent(
    state: ReceiptState,
    event: (ReceiptEvent) -> Unit
){
    Scaffold(
        topBar = {
            ReceiptTopBar(
                showMenuIcon = !state.previewMode,
                onUpdateBookmark = {
                    event.invoke(ReceiptEvent.OnUpdateBookmark(state.transactionId))
                },
                onDelete = {
                    event.invoke(ReceiptEvent.OnDelete(state.transactionId))
                },
                onNavigateBack = {
                    event.invoke(ReceiptEvent.OnNavigateBack)
                }
            )
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()

        Column(Modifier.padding(innerPadding)
            .fillMaxSize()
            .background(Surface)
            .padding(vertical = 16.dp, horizontal = 24.dp)
        ){
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
                if(!state.previewMode){
                    Text(
                        text = state.transactionCreatedAt,
                        style = KasirMudahTypography.bodyLarge
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.transaction_id, state.transactionId),
                        style = KasirMudahTypography.labelSmall
                    )
                }
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
                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.name_count, item.itemName, item.count),
                            style = KasirMudahTypography.labelMedium
                        )
                        Text(
                            text = item.totalPrice,
                            style = KasirMudahTypography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
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
                        text = state.totalTransaction,
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
            if(state.previewMode){
                RoundedPrimaryButton(
                    text = stringResource(R.string.save)
                ) {
                    event.invoke(ReceiptEvent.OnSave)
                }
                Spacer(Modifier.height(8.dp))
                RoundedOutlinedButton(
                    text = stringResource(R.string.cancel)
                ) {
                    event.invoke(ReceiptEvent.OnNavigateBack)
                }
            }else{
                RoundedPrimaryButton(
                    text = stringResource(R.string.download_receipt)
                ) {
                    event.invoke(ReceiptEvent.OnDownload)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReceiptContentPrev(){
    ReceiptContent(
        ReceiptState(
            shopName = "Toko Madura A",
            transactionCreatedAt = "12 Agustus 2026 - 12:53:01",
            transactionId = "4shisefhw48t4",
            transactionShopItems = MutableList(6){
                ReceiptItem(
                    itemName = "Barang Nomor $it",
                    totalPrice = "Rp 863.000,00",
                    count = 5
                )
            },
            totalTransaction = "Rp 1.500.255,00"
        )
    ){}
}