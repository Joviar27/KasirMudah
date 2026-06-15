package com.cobasendiri.kasirmudah.ui.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.ui.theme.Tertiary

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    maxCharacter: Int = 40,
    background: Color = Surface,
    showTopLabel: Boolean = true,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) OnPrimaryVariant else Tertiary,
    )

    val focusManager = LocalFocusManager.current

    Column(modifier) {
        if(isFocused && showTopLabel){
            Text(
                label,
                style = KasirMudahTypography.bodyMedium
            )
            Spacer(Modifier.height(4.dp))
        }
        BasicTextField(
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged{
                    isFocused = it.isFocused
                },
            value = value,
            textStyle = KasirMudahTypography.bodyLarge,
            singleLine = true,
            maxLines = 1,
            decorationBox = { innerTextField ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(background, RoundedCornerShape(16.dp))
                    .border(1.dp, borderColor, RoundedCornerShape(16.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier
                        .width(IntrinsicSize.Max)
                        .padding(vertical = 16.dp)
                        .padding(start = 16.dp)
                    ){
                        if (value.isEmpty()) {
                            Text(
                                label,
                                style = KasirMudahTypography.bodyLarge
                                    .copy(color = OnPrimary.copy(alpha = 0.5f))
                            )
                        }
                        innerTextField()
                    }
                }
            },
            onValueChange = {
                if(it.length <= maxCharacter){
                    onValueChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldPrev() {
    Box(Modifier.padding(16.dp)
        .fillMaxWidth()
    ){
        InputField(
            label = "Template Barang Pertama",
            value = "Nama Barang"
        ){}
    }
}
