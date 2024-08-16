package com.the.changeitem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.the.changeitem.prezent.AddChangeItemScreen

import com.the.changeitem.prezent.ChangeItemScreenFun
import com.the.changeitem.prezent.Screen
import com.the.changeitem.ui.theme.ChangeItemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChangeItemTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ChangeItemScreen.route
                    ) {
                        composable(route = Screen.ChangeItemScreen.route) {
                            ChangeItemScreenFun(navController = navController)
                        }
                        composable(route = Screen.ChangeItemScreen_Enter.route + "?itemId={itemId}&itemColor={itemColor}",
                            arguments = listOf(
                                navArgument(name = "itemId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }, navArgument(name = "itemColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                })
                        ) {
                            val color = it.arguments?.getInt("itemColor") ?: -1
                            AddChangeItemScreen(navController = navController, itemColor = color)
                        }
                    }

                }

            }
        }
    }
}

