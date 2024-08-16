package com.the.changeitem.domain.util

sealed class ChangeItemOrder (val orderType: OrderType){
    class Title(orderType: OrderType):ChangeItemOrder(orderType)
    class Color(orderType: OrderType):ChangeItemOrder(orderType)
    class Date(orderType: OrderType):ChangeItemOrder(orderType)

    fun copy(orderType: OrderType):ChangeItemOrder{
        return when(this){
            is Title-> Title(orderType)
            is Date-> Date(orderType)
            is Color-> Color(orderType)
        }
    }
}