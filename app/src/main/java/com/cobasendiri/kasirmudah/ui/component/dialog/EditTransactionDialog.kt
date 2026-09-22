package com.cobasendiri.kasirmudah.ui.component.dialog 
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.component.button.RoundedOutlinedButton
import com.cobasendiri.kasirmudah.ui.component.button.RoundedPrimaryButton
import com.cobasendiri.kasirmudah.ui.component.inputfield.InputField
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun EditTransactionDialog(
    name: String,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onSave: (String) -> Unit
) {

    var nameDraft by remember { mutableStateOf(name) }

    val isSaveButtonEnabled = remember(nameDraft){
        nameDraft.let {
            it.isNotBlank() && it.firstOrNull()?.isWhitespace() == false
        }
    }

    BaseDialog(onDismiss = onDismiss) {
        Column(Modifier.background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.edit_transaction),
                style = KasirMudahTypography.headlineSmall
            )
            Spacer(Modifier.height(24.dp))
            InputField(
                label = stringResource(R.string.transaction_name),
                value = nameDraft,
            ) {
                nameDraft = it
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
                    modifier = Modifier.width(140.dp),
                    text = stringResource(R.string.save),
                    isEnabled = isSaveButtonEnabled
                ) {
                    onSave.invoke(nameDraft)
                }
            }
        }
    }
}

@Preview
@Composable
fun EditTransactionDialogPrev(){
    EditTransactionDialog("Tes Nama",{},{},{})
}