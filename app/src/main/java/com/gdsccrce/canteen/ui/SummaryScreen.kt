package com.gdsccrce.canteen.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gdsccrce.canteen.R
import com.gdsccrce.canteen.data.OrderUiState
import com.gdsccrce.canteen.ui.components.FormattedPriceLabel


@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,

    onCancelButtonClicked : ()->Unit = {},

    onSendButtonClicked : (String, String ) -> Unit,
    modifier: Modifier = Modifier
){
    val resources = LocalContext.current.resources

    val numberOfCupcakes = resources.getQuantityString(
        R.plurals.items,
        orderUiState.quantity,
        orderUiState.quantity
    )
    //Load and format a string resource with the parameters.
    val orderSummary = stringResource(
        R.string.order_details,
        numberOfCupcakes,
        orderUiState.item,
        orderUiState.venue,
        orderUiState.quantity
    )
    val newOrder = stringResource(R.string.new_canteen_app)

    val items = listOf(

        Pair(stringResource(R.string.item), numberOfCupcakes),

        Pair(stringResource(R.string.quantity), orderUiState.item),

        Pair(stringResource(R.string.venue), orderUiState.venue)
    )

    Column (
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Text(item.first.uppercase())
            Text(text = item.second, fontWeight = FontWeight.Bold)
            Divider(thickness = 10.dp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        FormattedPriceLabel(
            subtotal = orderUiState.price,
            modifier = Modifier.align(Alignment.End)
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {onSendButtonClicked(newOrder , orderSummary)}
        ) {
            Text(stringResource(R.string.send))
        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onCancelButtonClicked
        ) {
            Text(stringResource(R.string.cancel))
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun OrderSummaryPreview(){
//    OrderSummaryScreen(
//        orderUiState = OrderUiState(0, "Test", "Test", "$300.00"),
//    )
//}

