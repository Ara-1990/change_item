package com.the.changeitem.domain.usecases
import com.the.changeitem.domain.model.ChangeItemInValidException
import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.repository.ChangeItemRepository

class AddItemUseCase (private val repository: ChangeItemRepository) {

    @Throws(ChangeItemInValidException::class)
    suspend  operator fun invoke(changeItem: ChangeItem){
        if (changeItem.title.isBlank()){
            throw ChangeItemInValidException("Title can't be empty")
        }
        if (changeItem.contents.isBlank()){
            throw ChangeItemInValidException(" Content can't  be empty")
        }
        repository.insertItem(changeItem)
    }
}