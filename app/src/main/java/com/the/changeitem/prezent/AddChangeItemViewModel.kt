package com.the.changeitem.prezent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.the.changeitem.domain.model.ChangeItemInValidException
import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.usecases.ItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddChangeItemViewModel @Inject constructor(
    private val itemUseCase: ItemUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _changeItemTitle = mutableStateOf(
        ChangeItemTextFieldState(
            hint = "Enter title..."
        )
    )
    val changeItemTitle: State<ChangeItemTextFieldState> = _changeItemTitle

    private val _changeItemContent = mutableStateOf(ChangeItemTextFieldState(hint = "Enter some content"))
    val changeItemContent: State<ChangeItemTextFieldState> = _changeItemContent

    private val _changeItemColor = mutableStateOf(ChangeItem.itemColors.random().toArgb())
    val changeItemColor: State<Int> = _changeItemColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentItemId: Int? = null

    init {
        savedStateHandle.get<Int>("itemId")?.let { itemId ->
            if (itemId != -1) {
                viewModelScope.launch {
                    itemUseCase.getItemUseCase(itemId)?.also { changeItem ->
                        currentItemId = changeItem.id
                        _changeItemTitle.value =
                            changeItemTitle.value.copy(text = changeItem.title, isHintVisible = false)
                        _changeItemContent.value =
                            changeItemContent.value.copy(text = changeItem.contents, isHintVisible = false)
                        _changeItemColor.value = changeItem.color
                    }
                }
            }

        }
    }

    fun onEvent(event: AddChangeItemEvent) {
        when (event) {
            is AddChangeItemEvent.SaveItem -> {
                viewModelScope.launch {
                    try {
                        itemUseCase.addItemUseCase(
                            ChangeItem(
                                title = changeItemTitle.value.text,
                                contents = changeItemContent.value.text,
                                time = System.currentTimeMillis(),
                                color = changeItemColor.value,
                                id = currentItemId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveChangeItem)

                    } catch (e: ChangeItemInValidException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Can not save item"
                            )
                        )

                    }
                }
            }
            is AddChangeItemEvent.ColorChange -> _changeItemColor.value = event.color

            is AddChangeItemEvent.ContentChanfeFocus -> _changeItemContent.value =
                changeItemContent.value.copy(isHintVisible = !event.content_focus.isFocused && changeItemContent.value.text.isBlank())

            is AddChangeItemEvent.ChangeFocusTitle ->_changeItemTitle.value = changeItemTitle.value.copy(isHintVisible = !event.title_focus.isFocused && changeItemTitle.value.text.isBlank())

            is AddChangeItemEvent.ContentEnter -> _changeItemContent.value =
                _changeItemContent.value.copy(text = event.content_value)

            is AddChangeItemEvent.TitleEnter -> _changeItemTitle.value =
                changeItemTitle.value.copy(text = event.title_value)
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveChangeItem : UiEvent()


    }
}