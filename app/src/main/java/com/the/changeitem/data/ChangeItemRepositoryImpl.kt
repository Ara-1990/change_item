package com.the.changeitem.data
import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.repository.ChangeItemRepository
import kotlinx.coroutines.flow.Flow

class ChangeItemRepositoryImpl (private val dao: ChangeItemDao): ChangeItemRepository {
    override fun getItem(): Flow<List<ChangeItem>> {
        return dao.getItem()
    }

    override suspend fun getItemById(id: Int): ChangeItem? {
        return dao.getItemById(id)

    }

    override suspend fun insertItem(changeItem: ChangeItem) {
        return dao.insertItem(changeItem)

    }

    override suspend fun deleteItem(changeItem: ChangeItem) {
        return dao.deleteItem(changeItem)

    }
}