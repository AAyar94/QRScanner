package com.aayar94.qrscanner.presentation.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aayar94.qrscanner.core.theme.GrayBlack
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    @DrawableRes icon: Int,
    hasSwitch: Boolean = false,
    switchChecked: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(Yellow, RoundedCornerShape(12.dp))
            .clickable {
                onClick.invoke()
            }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .offset(y = (-1).dp)
                .background(GrayBlack, RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(painterResource(id = icon), contentDescription = "Icon", tint = Color.White)
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = name, color = Color.White)
                    Text(text = description, color = Color.White)
                }
                if (hasSwitch) {
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = switchChecked,
                        onCheckedChange = { onClick.invoke() },
                        modifier = Modifier.padding(start = 16.dp),
                        thumbContent = {
                            if (switchChecked) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = "Checked",
                                    tint = Color.White
                                )
                            }
                        })
                }
            }
        }
    }
}


@Preview
@Composable
private fun SettingsItemPreview() {
    QRScannerTheme {
        Column {
            SettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                name = "Vibrate",
                description = "vibrate when scan done",
                icon = com.aayar94.qrscanner.R.drawable.ic_vibrate,
                hasSwitch = true,
                switchChecked = true,
                onClick = {}
            )
            SettingsItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                name = "Rate Us",
                description = "Your best reward to us",
                icon = com.aayar94.qrscanner.R.drawable.ic_rate,
                hasSwitch = false,
                switchChecked = false,
                onClick = {}
            )
        }
    }
}