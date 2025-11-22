package com.example.tracklog.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun DayDetailScreen(
    dateMillis: Long,
    viewModel: TrackLogViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    onAddEntry: () -> Unit
) {
    val trainings by viewModel.getTrainingsForDate(dateMillis).collectAsState()
    val competitions by viewModel.getCompetitionsForDate(dateMillis).collectAsState()
    
    val date = Instant.ofEpochMilli(dateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Trainings", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(trainings) { training ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Desc: ${training.description}")
                        Text("Dist: ${training.distanceMeters}m")
                        Text("Times: ${training.times.joinToString(", ")}")
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Competitions", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(competitions) { comp ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Name: ${comp.name}")
                        Text("Event: ${comp.event}")
                        Text("Result: ${comp.result}")
                        Text("Pos: ${comp.position}")
                    }
                }
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = navigateBack) {
                Text("Back")
            }
            Button(onClick = onAddEntry) {
                Text("Add Entry")
            }
        }
    }
}
