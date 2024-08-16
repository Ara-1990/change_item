package com.the.changeitem.domain.usecases

class ItemUseCase(
    val addItemUseCase: AddItemUseCase,
    val deleteItemUseCase: DeleteItemUseCase,
    val getItemsUseCase: GetItemsUseCase,
    val getItemUseCase: GetItemUseCase

) {
}