package com.example.tracklog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracklog.data.Competition
import com.example.tracklog.data.Training
import com.example.tracklog.data.TrainingDistance
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(
    dateMillis: Long,
    viewModel: TrackLogViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit
) {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Training", "Competition")

    // Training State
    var tDescription by remember { mutableStateOf("") }
    var tNotes by remember { mutableStateOf("") }
    // List of distances. Each element contains distance, times, and current time input
    // We use a class with proper state management for Compose
    class LocalDistanceState {
        var distance by mutableStateOf("")
        var times = mutableStateListOf<String>()
        var currentTimeInput by mutableStateOf("")
    }
    val tDistances = remember { mutableStateListOf<LocalDistanceState>() }

    // Competition State
    var cName by remember { mutableStateOf("") }
    var cLocation by remember { mutableStateOf("AL") } // AL or PC
    
    class LocalEventState {

        var performance by mutableStateOf("")
        var distanceMeters by mutableStateOf("")
        var wind by mutableStateOf("")
        
        // Helper to check if wind is needed
        val isWindNeeded: Boolean
            get() {
                val dist = distanceMeters.toIntOrNull() ?: 0
                return cLocation == "AL" && dist > 0 && dist < 400
            }
            
        // Helper to check validity
        val isInvalid: Boolean
            get() {
                val w = wind.toDoubleOrNull()
                return isWindNeeded && w != null && w > 2.0
            }
    }
    val cEvents = remember { mutableStateListOf<LocalEventState>() }

    // Load existing data
    LaunchedEffect(dateMillis) {
        val training = viewModel.getTrainingForDate(dateMillis)
        if (training != null) {
            selectedTab = 0
            tDescription = training.description
            tNotes = training.notes
            tDistances.clear()
            training.distances.forEach { dist ->
                val state = LocalDistanceState()
                state.distance = dist.distanceMeters.toString()
                state.times.addAll(dist.times)
                tDistances.add(state)
            }
        } else {
            val competition = viewModel.getCompetitionForDate(dateMillis)
            if (competition != null) {
                selectedTab = 1
                cName = competition.name
                cLocation = competition.location
                cEvents.clear()
                competition.events.forEach { event ->
                    val state = LocalEventState()

                    state.performance = event.performance
                    state.distanceMeters = event.distanceMeters?.toString() ?: ""
                    state.wind = event.wind ?: ""
                    cEvents.add(state)
                }
            } else {
                // Default: Add one empty distance block for training and one for competition
                if (tDistances.isEmpty()) {
                    tDistances.add(LocalDistanceState())
                }
                if (cEvents.isEmpty()) {
                    cEvents.add(LocalEventState())
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedTab == 0) {
            // Training Form
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = tDescription,
                        onValueChange = { tDescription = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                itemsIndexed(tDistances) { index, distanceState ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = distanceState.distance,
                                    onValueChange = { distanceState.distance = it },
                                    label = { Text("Distance (m)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(onClick = { tDistances.removeAt(index) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remove Distance")
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Times list
                            if (distanceState.times.isNotEmpty()) {
                                Text("Times:", style = MaterialTheme.typography.labelMedium)
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    distanceState.times.forEachIndexed { timeIndex, time ->
                                        InputChip(
                                            selected = false,
                                            onClick = { distanceState.times.removeAt(timeIndex) },
                                            label = { Text(time) },
                                            trailingIcon = { Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp)) }
                                        )
                                    }
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedTextField(
                                    value = distanceState.currentTimeInput,
                                    onValueChange = { distanceState.currentTimeInput = it },
                                    label = { Text("Time (s)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {
                                    if (distanceState.currentTimeInput.isNotBlank()) {
                                        distanceState.times.add(distanceState.currentTimeInput)
                                        distanceState.currentTimeInput = ""
                                    }
                                }) {
                                    Text("Add Time")
                                }
                            }
                        }
                    }
                }

                item {
                    Button(
                        onClick = { tDistances.add(LocalDistanceState()) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Another Distance")
                    }
                }

                item {
                    OutlinedTextField(
                        value = tNotes,
                        onValueChange = { tNotes = it },
                        label = { Text("Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Button(
                onClick = {
                    val distances = tDistances.map {
                        TrainingDistance(
                            distanceMeters = it.distance.toIntOrNull() ?: 0,
                            times = it.times.toList()
                        )
                    }.filter { it.distanceMeters > 0 } // Filter out empty/invalid distances

                    val training = Training(
                        date = dateMillis,
                        description = tDescription,
                        distances = distances,
                        notes = tNotes
                    )
                    viewModel.saveTraining(training)
                    navigateBack()
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Save Training")
            }

        } else {
            // Competition Form
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = cName,
                        onValueChange = { cName = it },
                        label = { Text("Competition Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FilterChip(
                            selected = cLocation == "AL",
                            onClick = { cLocation = "AL" },
                            label = { Text("Aire Libre") }
                        )
                        FilterChip(
                            selected = cLocation == "PC",
                            onClick = { cLocation = "PC" },
                            label = { Text("Pista Cubierta") }
                        )
                    }
                }

                itemsIndexed(cEvents) { index, eventState ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Event ${index + 1}", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = { cEvents.removeAt(index) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Remove Event")
                                }
                            }
                            

                            Row(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = eventState.distanceMeters,
                                    onValueChange = { eventState.distanceMeters = it },
                                    label = { Text("Distance (m)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(
                                    value = eventState.performance,
                                    onValueChange = { eventState.performance = it },
                                    label = { Text("Performance") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            if (eventState.isWindNeeded) {
                                OutlinedTextField(
                                    value = eventState.wind,
                                    onValueChange = { eventState.wind = it },
                                    label = { Text("Wind (m/s)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Allow negative? Yes, Number usually allows it or we can use Text
                                    modifier = Modifier.fillMaxWidth()
                                )
                                if (eventState.isInvalid) {
                                    Text(
                                        text = "Invalid Performance (Wind > 2.0)",
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Button(
                        onClick = { cEvents.add(LocalEventState()) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Another Event")
                    }
                }
            }

            Button(
                onClick = {
                    val events = cEvents.map {
                        com.example.tracklog.data.CompetitionEvent(
                            performance = it.performance,
                            distanceMeters = it.distanceMeters.toIntOrNull(),
                            wind = if (it.isWindNeeded) it.wind else null,
                            isInvalid = it.isInvalid
                        )
                    }.filter { it.distanceMeters != null && it.distanceMeters > 0 }

                    val competition = Competition(
                        date = dateMillis,
                        name = cName,
                        location = cLocation,
                        events = events
                    )
                    viewModel.saveCompetition(competition)
                    navigateBack()
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Save Competition")
            }
        }
    }
}
