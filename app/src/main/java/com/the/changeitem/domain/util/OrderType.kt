package com.the.changeitem.domain.util

sealed class OrderType {
object Increasing:OrderType()
object Decreasing:OrderType()
}