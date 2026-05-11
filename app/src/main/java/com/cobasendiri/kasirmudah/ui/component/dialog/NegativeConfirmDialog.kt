package com.cobasendiri.kasirmudah.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.component.button.RoundedNegativeButton
import com.cobasendiri.kasirmudah.ui.component.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudah.ui.component.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun NegativeConfirmDialog(
    title: String,
    body: String,
    cancelButton: String,
    confirmButton: String,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    BaseDialog(
        onDismiss = onDismiss
    ){
        Column(Modifier.background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = KasirMudahTypography.headlineSmall
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = body,
                textAlign = TextAlign.Center,
                style = KasirMudahTypography.bodyMedium
            )
            Spacer(Modifier.height(32.dp))
            Row {
                RoundedOutlinedButton(
                    modifier = Modifier.width(140.dp),
                    text = cancelButton
                ){
                    onCancel.invoke()
                }
                Spacer(Modifier.width(8.dp))
                RoundedNegativeButton(
                    modifier = Modifier.width(140.dp),
                    text = confirmButton
                ) {
                    onConfirm.invoke()
                }
            }
        }
    }
}

@Preview
@Composable
fun NegativeConfirmDialogPrev() {
    NegativeConfirmDialog(
        title = "Judul Dialog",
        body = "Konten dari dialog yang kadang cukup panjang untuk ditampilkan ini cman contoh ya",
        cancelButton = "Batal",
        confirmButton = "Hapus",
        onDismiss = {},
        onCancel = {},
        onConfirm = {}
    )
}