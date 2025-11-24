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
                    // FAB on Calendar now goes to DayDetail to enforce type selection logic
                    navController.navigate("${TrackLogScreen.DayDetail.name}/$date")
                }
            )
        }
        
        composable(
            route = "${TrackLogScreen.AddEntry.name}/{date}?type={type}",
            arguments = listOf(
                navArgument("date") { type = NavType.LongType },
                navArgument("type") { nullable = true; type = NavType.StringType }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getLong("date") ?: System.currentTimeMillis()
            val type = backStackEntry.arguments?.getString("type")
            AddEntryScreen(
                dateMillis = date,
                initialType = type,
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
                onAddEntry = { type ->
                    navController.navigate("${TrackLogScreen.AddEntry.name}/$date?type=$type")
                }
            )
        }
    }
}
