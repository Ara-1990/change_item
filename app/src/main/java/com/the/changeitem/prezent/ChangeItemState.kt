package com.the.changeitem.prezent

import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.util.ChangeItemOrder
import com.the.changeitem.domain.util.OrderType

data class ChangeItemState(

    val changeItems: List<ChangeItem> = emptyList(),
    val itemsOrder: ChangeItemOrder = ChangeItemOrder.Date(OrderType.Increasing),
    val isOrderSectionVisible: Boolean = false,
)

