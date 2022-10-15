package com.gdsccrce.canteen.ui

import androidx.lifecycle.ViewModel
import com.gdsccrce.canteen.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_ITEM = 50.00

class OrderViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()
    fun setColor(currentColor: Int) {

    }

    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                venue = pickupDate,
                price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes,
                price = calculatePrice(quantity = numberCupcakes)
            )
        }
    }


    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            currentState.copy(item = desiredFlavor)
        }
    }

    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.venue
    ): String {
        var calculatedPrice = quantity * PRICE_PER_ITEM
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        return formattedPrice
    }


    private fun pickupOptions(): List<String> {
//        val dateOptions = mutableListOf<String>()
//        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
//        val calendar = Calendar.getInstance()
//
//        repeat(4) {
//            dateOptions.add(formatter.format(calendar.time))
//            calendar.add(Calendar.DATE, 1)
//        }
//        return dateOptions
//    }
        val venueOptions = mutableListOf<String>()
        venueOptions.add("501")
        venueOptions.add("202")
        venueOptions.add("303")
        venueOptions.add("104")
        venueOptions.add("602")
        venueOptions.add("702")
        return venueOptions
    }
}