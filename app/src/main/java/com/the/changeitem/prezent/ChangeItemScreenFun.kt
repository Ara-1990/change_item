package com.the.changeitem.prezent

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.the.changeitem.prezent.Screen
import kotlinx.coroutines.launch

@Composable
fun ChangeItemScreenFun(
    changeItemViewModel: ChangeItemViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = changeItemViewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate(Screen.ChangeItemScreen_Enter.route) },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add item")

        }
    }, scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Your items", style = MaterialTheme.typography.h4)
                IconButton(onClick = { changeItemViewModel.onEvent(ChangeItemEvent.OrderSectionToggle) }) {
                    Icon(imageVector = Icons.Default.Sailing, contentDescription = "Sailing")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    changeItemOrder = state.itemsOrder,
                    onOrderChange = {
                        changeItemViewModel.onEvent(ChangeItemEvent.Order(it))
                    }
                )

            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.changeItems) { item ->
                    ChangeItemFun(
                        changeItem = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.ChangeItemScreen_Enter.route + "?itemId=${item.id}&itemColor=${item.color}")
                            }, onDeleteClick = {
                            changeItemViewModel.onEvent(ChangeItemEvent.DeleteItem(item))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "item deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    changeItemViewModel.onEvent(ChangeItemEvent.ItemRestore)
                                }
                            }
                        })
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }

    }

}