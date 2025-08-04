package com.aayar94.qrscanner.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow

@Composable
fun PageHeader(
    modifier: Modifier = Modifier,
    onLeftAction: () -> Unit,
    leftActionIcon: ImageVector,
    leftActionDescription: String,
    title: String,
    onRightAction: (() -> Unit)? = null,
    rightActionIcon: ImageVector? = null,
    rightActionDescription: String? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { onLeftAction.invoke() },
                modifier = Modifier
                    .background(Gray, shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Yellow, shape = RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = leftActionIcon,
                    contentDescription = leftActionDescription,
                    tint = Yellow
                )
            }
            Spacer(Modifier.width(12.dp))
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
            Spacer(Modifier.weight(1f))
            onRightAction?.let {
                rightActionIcon?.let { imageVector ->
                    IconButton(
                        onClick = { onRightAction.invoke() },
                        modifier = Modifier
                            .background(Gray, shape = RoundedCornerShape(12.dp))
                            .border(1.dp, Yellow, shape = RoundedCornerShape(12.dp))
                    ) {

                        Icon(
                            imageVector = imageVector,
                            contentDescription = rightActionDescription,
                            tint = Yellow
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun PageHeaderPreview() {
    QRScannerTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            PageHeader(
                modifier = Modifier.fillMaxWidth(),
                onLeftAction = {},
                leftActionIcon = Icons.AutoMirrored.Outlined.ArrowBack,
                leftActionDescription = "Back",
                title = "Screen",
                onRightAction = {},
                rightActionIcon = Icons.Outlined.Settings,
                rightActionDescription = "Settings"
            )
            PageHeader(
                modifier = Modifier.fillMaxWidth(),
                onLeftAction = {},
                leftActionIcon = Icons.AutoMirrored.Outlined.ArrowBack,
                leftActionDescription = "Back",
                title = "Screen",
                onRightAction = null,
                rightActionIcon = null,
                rightActionDescription = null
            )
        }

    }
}