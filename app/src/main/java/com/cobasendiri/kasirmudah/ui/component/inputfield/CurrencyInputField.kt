package com.cobasendiri.kasirmudah.ui.component.inputfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.utils.FormatUtil.decimalFormat
import com.cobasendiri.kasirmudah.ui.utils.FormatUtil.rawFormat

@Composable
fun CurrencyInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    maxCharacter: Int = 40,
    background: Color = Surface,
    showTopLabel: Boolean = true,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    val textFieldValue by remember(value){
        mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    }

    val formattedInput = remember(textFieldValue){
        val formattedInput = textFieldValue.text.decimalFormat()
        val lengthDiff = formattedInput.length - textFieldValue.text.length
        val newCursorPosition = (textFieldValue.selection.end + lengthDiff)
            .coerceIn(0, formattedInput.length)

        TextFieldValue(
            text = formattedInput,
            selection = TextRange(newCursorPosition)
        )
    }

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
            value = formattedInput,
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
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Rp",
                        style = KasirMudahTypography.bodyLarge
                    )
                    VerticalDivider(
                        Modifier.fillMaxHeight(),
                        thickness = 1.dp,
                        color = borderColor
                    )
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
                    if(value.isNotEmpty()){
                        Text(
                            ",00",
                            style = KasirMudahTypography.bodyLarge
                        )
                    }
                }
            },
            onValueChange = {
                val newValue = it.text.rawFormat()
                if(textFieldValue.text.length <= maxCharacter){
                    onValueChange(newValue)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType =  KeyboardType.Number,
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
fun CurrencyInputFieldPrev() {
    Box(Modifier.padding(16.dp)
        .fillMaxWidth()
    ){
        CurrencyInputField(
            label = "Template Barang Pertama",
            value = "5000",
        ){}
    }
}
