package com.gdsccrce.canteen.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gdsccrce.canteen.R

@Composable
fun FormattedPriceLabel(subtotal: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.subtotal_price, subtotal),
        modifier = modifier
    )
}
