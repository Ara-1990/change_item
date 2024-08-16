package com.the.changeitem.prezent

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.the.changeitem.domain.util.ChangeItemOrder
import com.the.changeitem.domain.util.OrderType


@Composable
fun OrderSection (
    modifier: Modifier = Modifier,
    changeItemOrder: ChangeItemOrder = ChangeItemOrder.Date(OrderType.Decreasing),
    onOrderChange: (ChangeItemOrder) -> Unit
) {

    Column(modifier = modifier) {

            RadioButton(
                text = "Title",
                selected = changeItemOrder is ChangeItemOrder.Title,
                onSelect = { onOrderChange(ChangeItemOrder.Title(changeItemOrder.orderType)) }
            )
            RadioButton(
                text = "Date",
                selected = changeItemOrder is ChangeItemOrder.Date,
                onSelect = { onOrderChange(ChangeItemOrder.Date(changeItemOrder.orderType)) })

            RadioButton(
                text = "Color",
                selected = changeItemOrder is ChangeItemOrder.Color,
                onSelect = { onOrderChange(ChangeItemOrder.Color(changeItemOrder.orderType)) })




            RadioButton(
                text = "Increasing",
                selected = changeItemOrder.orderType is OrderType.Increasing,
                onSelect = { onOrderChange(changeItemOrder.copy(OrderType.Increasing)) })

            RadioButton(
                text = "Decreasing",
                selected = changeItemOrder.orderType is OrderType.Decreasing,
                onSelect = { onOrderChange(changeItemOrder.copy(OrderType.Decreasing)) })


    }
}