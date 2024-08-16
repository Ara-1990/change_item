package com.the.changeitem.prezent

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.the.changeitem.domain.model.ChangeItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddChangeItemScreen(
    navController: NavController,
    itemColor: Int,
    viewModel: AddChangeItemViewModel = hiltViewModel()
) {
    val titleState = viewModel.changeItemTitle.value
    val contentState = viewModel.changeItemContent.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddChangeItemViewModel.UiEvent.SaveChangeItem -> navController.navigateUp()
                is AddChangeItemViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }

        }
    }
    val itemBackgroundAnimatable = remember {
        Animatable(
            Color(if (itemColor != -1) itemColor else viewModel.changeItemColor.value)
        )
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.onEvent(AddChangeItemEvent.SaveItem) },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Default.Save, contentDescription = "Save item")
        }
    }, scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(itemBackgroundAnimatable.value)
                .padding(16.dp),

        ) {
            Column(
                modifier = Modifier
                    .height(400.dp)
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            )
            {
                ChangeItem.itemColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp, color = if (viewModel.changeItemColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    itemBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(durationMillis = 500)
                                    )
                                }
                                viewModel.onEvent(AddChangeItemEvent.ColorChange(colorInt))
                            }
                    )

                }

            }

            Spacer(modifier = Modifier.height(20.dp))
            TextFieldTransperent(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddChangeItemEvent.TitleEnter(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddChangeItemEvent.ChangeFocusTitle(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5

            )
            Spacer(modifier = Modifier.height(20.dp))

            TextFieldTransperent(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddChangeItemEvent.ContentEnter(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddChangeItemEvent.ContentChanfeFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()

            )

        }

    }

}