package com.example.tracklog.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

enum class TrackLogScreen {
    Calendar,
    AddEntry,
    DayDetail
}

@Composable
fun TrackLogApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TrackLogScreen.Calendar.name
    ) {
        composable(TrackLogScreen.Calendar.name) {
            CalendarScreen(
                onDateSelected = { date ->
                    navController.navigate("${TrackLogScreen.DayDetail.name}/$date")
                },
                onAddEntry = { date ->
                    navController.navigate("${TrackLogScreen.AddEntry.name}/$date")
                }
            )
        }
        
        composable(
            route = "${TrackLogScreen.AddEntry.name}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.LongType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getLong("date") ?: System.currentTimeMillis()
            AddEntryScreen(
                dateMillis = date,
                navigateBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = "${TrackLogScreen.DayDetail.name}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.LongType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getLong("date") ?: 0L
            DayDetailScreen(
                dateMillis = date,
                navigateBack = { navController.popBackStack() },
                onAddEntry = {
                    navController.navigate("${TrackLogScreen.AddEntry.name}/$date")
                }
            )
        }
    }
}
