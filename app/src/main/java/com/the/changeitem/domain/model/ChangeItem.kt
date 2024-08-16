package com.the.changeitem.domain.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.the.changeitem.ui.theme.*

@Entity
data class ChangeItem (

    val title: String,
    val contents: String,
    val time: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val itemColors = listOf(Purple200, Blue, Orange, Teal200, Pink)
    }
}


class ChangeItemInValidException(message: String) : Exception(message)
