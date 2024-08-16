package com.the.changeitem.domain.usecases
import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.repository.ChangeItemRepository

class DeleteItemUseCase (private val repository: ChangeItemRepository) {

    suspend operator fun invoke(changeItem: ChangeItem) {
        repository.deleteItem(changeItem)
    }
}