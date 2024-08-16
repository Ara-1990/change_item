package com.the.changeitem.data
import androidx.room.*
import com.the.changeitem.domain.model.ChangeItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ChangeItemDao {
    @Query("SELECT * FROM changeItem")
    fun getItem(): Flow<List<ChangeItem>>

    @Query("SELECT * FROM changeItem where id = :id")
    suspend fun getItemById(id: Int): ChangeItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(changeItem: ChangeItem)

    @Delete
    suspend fun deleteItem(changeItem: ChangeItem)
}