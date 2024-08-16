package com.the.changeitem.prezent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.the.changeitem.domain.model.ChangeItem
@Composable
fun ChangeItemFun (
    changeItem: ChangeItem,
    modifier: Modifier = Modifier,
    corner_radius: Dp = 10.dp,
    cornerRadiusCat: Dp = 30.dp,
    onDeleteClick: () -> Unit,

    ) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cornerRadiusCat.toPx(), 0f)
                lineTo(size.width, cornerRadiusCat.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(changeItem.color),
                    size = size,
                    cornerRadius = CornerRadius(corner_radius.toPx())
                )
                drawRoundRect(
                    color = Color(ColorUtils.blendARGB(changeItem.color, 0x000000, 0.2f)),
                    topLeft = Offset(size.width - cornerRadiusCat.toPx(), -100f),
                    size = Size(cornerRadiusCat.toPx() + 100f, cornerRadiusCat.toPx() + 100f),
                    cornerRadius = CornerRadius(corner_radius.toPx())
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = changeItem.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = changeItem.contents,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            onClick = { onDeleteClick.invoke() },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete item",
                tint = MaterialTheme.colors.onSurface
            )

        }
    }
}
