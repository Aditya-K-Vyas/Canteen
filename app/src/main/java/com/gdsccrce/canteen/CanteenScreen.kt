package com.gdsccrce.canteen



import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gdsccrce.canteen.data.DataSource.qty
import com.gdsccrce.canteen.data.DataSource.menuOptions
import com.gdsccrce.canteen.ui.OrderSummaryScreen
import com.gdsccrce.canteen.ui.OrderViewModel
import com.gdsccrce.canteen.ui.SelectOptionScreen
import com.gdsccrce.canteen.ui.StartOrderScreen

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */

enum class CupcakeScreen() {
    Start,
    Quantity,
    Venue,
    Summary
}

@Composable
fun CupcakeAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CanteenApp(modifier: Modifier = Modifier, viewModel: OrderViewModel = viewModel()) {

    val navController = rememberNavController()


    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = backStackEntry?.destination?.route?: CupcakeScreen.Start.name

    Scaffold(
        topBar = {
            CupcakeAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = modifier.padding(innerPadding),

            ) {
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = menuOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CupcakeScreen.Quantity.name)
                    })
            }
            composable(route = CupcakeScreen.Quantity.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = qty.map { id -> stringResource(id) },
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(
                            viewModel = viewModel,
                            navController = navController
                        )
                    },
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Venue.name) }
                )
            }

            composable(route = CupcakeScreen.Venue.name) {
                SelectOptionScreen(subtotal = uiState.price,
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(
                            viewModel,
                            navController
                        )
                    },
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) })

            }

            composable(route = CupcakeScreen.Summary.name) {
                val context = LocalContext.current
                OrderSummaryScreen(orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(
                            viewModel,
                            navController
                        )
                    },
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(
                            context,
                            subject = subject,
                            summary = summary
                        )
                    })
            }
        }

    }
}


private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_canteen_app)
        )
    )

}


private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name, false)


}

