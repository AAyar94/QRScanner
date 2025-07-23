package com.aayar94.qrscanner.presentation.generate_by_category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
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
import androidx.compose.ui.unit.sp
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow
import com.aayar94.qrscanner.domain.model.QRCategory

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: QRCategory,
    onCategorySelect: (Int) -> Unit
) {
    Box(
        modifier = modifier.clickable { onCategorySelect.invoke(category.id) },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Gray)
                .border(1.dp, Yellow, RoundedCornerShape(12.dp))
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp)) // Kenar yuvarlama hala olsun
                .then(Modifier), // Boş geçebiliriz veya modifiye edebiliriz
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = category.icon),
                contentDescription = stringResource(category.name)
            )
        }
        BasicText(
            text = stringResource(category.name),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 18.dp)
                .background(Yellow, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(8.sp, 12.sp, 0.5.sp)
        )
    }
}


@Preview
@Composable
private fun CategoryItemPreview() {
    QRScannerTheme {
        CategoryItem(
            modifier = Modifier.wrapContentSize(),
            category = QRCategory(1, R.string.category_text, R.drawable.ic_text),
            onCategorySelect = {}
        )
    }
}