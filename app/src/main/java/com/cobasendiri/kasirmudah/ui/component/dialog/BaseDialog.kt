package com.cobasendiri.kasirmudah.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable (() -> Unit)
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier.fillMaxWidth()
                .padding(16.dp)
                .background(White, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            content.invoke()
        }
    }
}

@Preview
@Composable
fun BaseDialogPrev() {
    BaseDialog(
        onDismiss = {}
    ){}
}