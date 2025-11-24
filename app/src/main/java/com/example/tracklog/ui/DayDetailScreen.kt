package com.example.tracklog.ui

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
    onAddEntry: (String) -> Unit
) {
    val trainings by viewModel.getTrainingsForDate(dateMillis).collectAsState(initial = emptyList())
    val competitions by viewModel.getCompetitionsForDate(dateMillis).collectAsState(initial = emptyList())
    
    val date = Instant.ofEpochMilli(dateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
    
    var showTypeSelectionDialog by remember { mutableStateOf(false) }

    if (showTypeSelectionDialog) {
        AlertDialog(
            onDismissRequest = { showTypeSelectionDialog = false },
            title = { Text("Select Activity Type") },
            text = { Text("Is this a training session or a competition?") },
            confirmButton = {
                Button(onClick = {
                    showTypeSelectionDialog = false
                    onAddEntry("Training")
                }) {
                    Text("Training")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showTypeSelectionDialog = false
                    onAddEntry("Competition")
                }) {
                    Text("Competition")
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = date.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            if (trainings.isNotEmpty() || competitions.isNotEmpty()) {
                IconButton(onClick = { viewModel.deleteDayContent(dateMillis) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Day Content")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (trainings.isNotEmpty()) {
            Text("Trainings", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(trainings) { training ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Desc: ${training.description}")
                            training.distances.forEach { distance ->
                                Text("Dist: ${distance.distanceMeters}m")
                                Text("Times: ${distance.times.joinToString(", ")}")
                            }
                            if (training.notes.isNotBlank()) {
                                Text("Notes: ${training.notes}")
                            }
                        }
                    }
                }
            }
        } else if (competitions.isNotEmpty()) {
            Text("Competitions", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(competitions) { comp ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Name: ${comp.name}")
                            Text("Location: ${comp.location}")
                            comp.events.forEach { event ->
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Distance: ${event.distanceMeters ?: 0}m")
                                Text("Result: ${event.performance}")
                                if (event.wind != null) {
                                    Text("Wind: ${event.wind}")
                                }
                                if (event.isInvalid) {
                                    Text("Invalid (Wind > 2.0)", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text("No entries for this day", style = MaterialTheme.typography.bodyLarge)
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = navigateBack) {
                Text("Back")
            }
            Button(onClick = {
                if (trainings.isNotEmpty()) {
                    onAddEntry("Training")
                } else if (competitions.isNotEmpty()) {
                    onAddEntry("Competition")
                } else {
                    showTypeSelectionDialog = true
                }
            }) {
                Text("Add Entry")
            }
        }
    }
}
