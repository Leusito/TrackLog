package com.example.tracklog.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracklog.data.Competition
import com.example.tracklog.data.Training
import java.time.Instant
import java.time.ZoneId

@Composable
fun AddEntryScreen(
    dateMillis: Long,
    viewModel: TrackLogViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Training", "Competition")
    
    // Common
    // dateMillis is passed in
    
    // Training fields
    var tDescription by remember { mutableStateOf("") }
    var tDistance by remember { mutableStateOf("") }
    var tTime by remember { mutableStateOf("") }
    var tNotes by remember { mutableStateOf("") }

    // Competition fields
    var cName by remember { mutableStateOf("") }
    var cEvent by remember { mutableStateOf("") }
    var cResult by remember { mutableStateOf("") }
    var cPosition by remember { mutableStateOf("") }

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
            OutlinedTextField(
                value = tDescription,
                onValueChange = { tDescription = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tDistance,
                onValueChange = { tDistance = it },
                label = { Text("Distance (m)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tTime,
                onValueChange = { tTime = it },
                label = { Text("Time (s)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tNotes,
                onValueChange = { tNotes = it },
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(
                onClick = {
                    val training = Training(
                        date = dateMillis,
                        description = tDescription,
                        distanceMeters = tDistance.toIntOrNull() ?: 0,
                        timeSeconds = tTime.toIntOrNull() ?: 0,
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
            OutlinedTextField(
                value = cName,
                onValueChange = { cName = it },
                label = { Text("Competition Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cEvent,
                onValueChange = { cEvent = it },
                label = { Text("Event (e.g. 100m)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cResult,
                onValueChange = { cResult = it },
                label = { Text("Result") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cPosition,
                onValueChange = { cPosition = it },
                label = { Text("Position") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            
            Button(
                onClick = {
                    val competition = Competition(
                        date = dateMillis,
                        name = cName,
                        event = cEvent,
                        result = cResult,
                        position = cPosition.toIntOrNull() ?: 0
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
