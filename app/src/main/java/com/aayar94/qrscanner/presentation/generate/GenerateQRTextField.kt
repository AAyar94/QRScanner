package com.aayar94.qrscanner.presentation.generate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow

@Composable
fun GenerateQRTextField(
    modifier: Modifier = Modifier,
    title: String,
    keyboardAction: ImeAction = ImeAction.Done,
    isPasteEnabled: Boolean = false,
    afterTextChanged: (String) -> Unit = {}
) {
    val text = remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text.value,
            onValueChange = { newValue: String ->
                text.value = newValue
                afterTextChanged(newValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .wrapContentHeight()
                .weight(1f)
                .border(
                    color = Color.White.copy(0.7f),
                    shape = RoundedCornerShape(4.dp),
                    width = 1.dp
                )
                .background(Color.Black.copy(0.7f), RoundedCornerShape(4.dp)),
            label = { Text(title) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                focusedContainerColor = Color.Black,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = Color.Black,
                disabledTextColor = Color.White,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = keyboardAction
            )
        )
        if (isPasteEnabled) {
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(
                onClick = {
                    val pasted = clipboardManager.getText()?.text.orEmpty()
                    if (pasted.isNotEmpty()) {
                        text.value = pasted
                        afterTextChanged(pasted)
                    }
                },
                modifier = Modifier
                    .background(Gray, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Yellow, shape = RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.Outlined.ContentPaste,
                    contentDescription = "Paste",
                    tint = Yellow
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0)
@Composable
private fun GenerateQRTextFieldPreview() {
    QRScannerTheme {
        GenerateQRTextField(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            title = "Title",
            isPasteEnabled = true
        )
    }
}