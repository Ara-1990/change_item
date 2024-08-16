package com.the.changeitem.domain.usecases
import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.repository.ChangeItemRepository

class GetItemUseCase (private val repository: ChangeItemRepository) {

    suspend operator fun invoke(id: Int): ChangeItem? {
        return repository.getItemById(id)
    }
}