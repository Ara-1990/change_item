package com.the.changeitem.prezent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.the.changeitem.domain.model.ChangeItem
import com.the.changeitem.domain.usecases.ItemUseCase
import com.the.changeitem.domain.util.ChangeItemOrder
import com.the.changeitem.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeItemViewModel @Inject constructor(private val itemUseCase: ItemUseCase) :
    ViewModel() {

    private val _state = mutableStateOf(ChangeItemState())
    val state: State<ChangeItemState> = _state

    private var deleteChangeItemRecently: ChangeItem? = null

    private var getItemJob: Job? = null


    init {
        getItems(ChangeItemOrder.Date(OrderType.Decreasing))
    }

    fun onEvent(itemsEvent: ChangeItemEvent) {
        when (itemsEvent) {
            is ChangeItemEvent.Order -> {
                if (state.value.itemsOrder::class == itemsEvent.changeItemOrder::class && state.value.itemsOrder.orderType == itemsEvent.changeItemOrder.orderType) {
                    return
                }
                getItems(itemsEvent.changeItemOrder)

            }

            is ChangeItemEvent.DeleteItem -> {
                deleteChangeItemRecently = itemsEvent.changeItem
                viewModelScope.launch {
                    itemUseCase.deleteItemUseCase(itemsEvent.changeItem)
                }

            }

            is ChangeItemEvent.ItemRestore -> {
                viewModelScope.launch {
                    itemUseCase.addItemUseCase(deleteChangeItemRecently ?: return@launch)
                    deleteChangeItemRecently = null

                }

            }

            is ChangeItemEvent.OrderSectionToggle -> {
                _state.value =
                    state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }
    }

    private fun getItems(itemsOrder: ChangeItemOrder) {
        getItemJob?.cancel()

        getItemJob = itemUseCase.getItemsUseCase(itemsOrder).onEach { items ->
            _state.value = state.value.copy(changeItems = items, itemsOrder = itemsOrder)
        }.launchIn(viewModelScope)
    }
}