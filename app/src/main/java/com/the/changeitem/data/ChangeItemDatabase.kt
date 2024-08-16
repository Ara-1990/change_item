package com.the.changeitem.data
import androidx.room.Database
import androidx.room.RoomDatabase
import com.the.changeitem.domain.model.ChangeItem

@Database(entities = [ChangeItem::class], version = 1)
abstract class ChangeItemDatabase: RoomDatabase(){
    abstract val changeItemDao:ChangeItemDao

    companion object{
        const val DATABASE_NAME="items_db"
    }
}