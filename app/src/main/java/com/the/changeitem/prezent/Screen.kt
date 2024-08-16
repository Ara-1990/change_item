package com.the.changeitem.prezent

sealed class Screen  (val route: String) {
    object ChangeItemScreen : Screen("changeItemScreen")
    object ChangeItemScreen_Enter : Screen("changeItemScreen_Enter")
}