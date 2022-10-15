package com.gdsccrce.canteen.data

data class OrderUiState(

    val quantity: Int = 0,

    val item: String = "",

    val venue: String = "",

    val price: String = "",

    val pickupOptions: List<String> = listOf()
)