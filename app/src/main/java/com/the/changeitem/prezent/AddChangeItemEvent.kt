package com.the.changeitem.prezent
import androidx.compose.ui.focus.FocusState

sealed class AddChangeItemEvent {
    data class TitleEnter(val title_value:String) : AddChangeItemEvent()
    data class ContentEnter(val content_value:String) : AddChangeItemEvent()
    data class ColorChange(val color:Int) : AddChangeItemEvent()
    data class ContentChanfeFocus(val content_focus: FocusState) : AddChangeItemEvent()
    data class ChangeFocusTitle(val title_focus:FocusState) : AddChangeItemEvent()
    object SaveItem:AddChangeItemEvent()

}