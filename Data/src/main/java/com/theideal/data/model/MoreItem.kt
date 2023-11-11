package com.theideal.data.model

data class MoreItem(
    val title: String,
    val label: String,
    val id: Int
){
    constructor(): this("", "", 0)
    constructor(title: String, id: Int): this(title, "", id)
}
