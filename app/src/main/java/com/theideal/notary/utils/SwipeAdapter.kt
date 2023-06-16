package com.theideal.notary.utils

interface SwipeAdapter {
    fun onItemDelete(position: Int)
    fun onItemEdit(position: Int)

    fun setDataChanged()
}