package com.the.changeitem.domain.repository
import com.the.changeitem.domain.model.ChangeItem
import kotlinx.coroutines.flow.Flow

interface ChangeItemRepository {

    fun getItem(): Flow<List<ChangeItem>>

    suspend fun getItemById(id:Int):ChangeItem?

    suspend fun insertItem(changeItem: ChangeItem)

    suspend fun deleteItem(changeItem: ChangeItem)
}