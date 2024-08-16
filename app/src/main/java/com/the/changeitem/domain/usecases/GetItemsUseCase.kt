package com.the.changeitem.domain.usecases
import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.repository.ChangeItemRepository
import com.the.changeitem.domain.util.ChangeItemOrder
import com.the.changeitem.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetItemsUseCase (private val repository: ChangeItemRepository) {

    operator fun invoke(notesOrder: ChangeItemOrder = ChangeItemOrder.Date(OrderType.Decreasing)): Flow<List<ChangeItem>> {
        return repository.getItem().map { item ->
            when (notesOrder.orderType) {
                is OrderType.Increasing -> {
                    when (notesOrder) {
                        is ChangeItemOrder.Title -> item.sortedBy { it.title.lowercase() }

                        is ChangeItemOrder.Color -> item.sortedBy { it.color }

                        is ChangeItemOrder.Date -> item.sortedBy { it.time }


                    }

                }

                is OrderType.Decreasing -> {
                    when (notesOrder) {
                        is ChangeItemOrder.Title -> item.sortedBy { it.title.lowercase() }

                        is ChangeItemOrder.Color -> item.sortedBy { it.color }

                        is ChangeItemOrder.Date -> item.sortedBy { it.time }

                    }
                }

            }
        }

    }
}