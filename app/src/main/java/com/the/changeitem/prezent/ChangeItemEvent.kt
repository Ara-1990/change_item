package com.the.changeitem.prezent
import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.util.ChangeItemOrder

sealed class ChangeItemEvent {
    data class Order(val changeItemOrder: ChangeItemOrder):ChangeItemEvent()
    data class DeleteItem(val changeItem: ChangeItem):ChangeItemEvent()
    object ItemRestore:ChangeItemEvent()
    object OrderSectionToggle:ChangeItemEvent()

}

