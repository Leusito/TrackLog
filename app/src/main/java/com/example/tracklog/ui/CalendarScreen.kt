package com.example.tracklog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracklog.data.Competition
import com.example.tracklog.data.Training
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScreen(
    viewModel: TrackLogViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onDateSelected: (Long) -> Unit,
    onAddEntry: (Long) -> Unit
) {
    val trainings by viewModel.allTrainings.collectAsState()
    val competitions by viewModel.allCompetitions.collectAsState()
    
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Month Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Text("<")
            }
            Text(
                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Text(">")
            }
        }

        // Days Grid
        val daysInMonth = currentMonth.lengthOfMonth()
        val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7 // 0 for Sunday, etc.
        
        val days = (1..daysInMonth).toList()
        val emptySlots = (0 until firstDayOfMonth).toList()

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.weight(1f)
        ) {
            // Weekday headers
            items(listOf("S", "M", "T", "W", "T", "F", "S")) { day ->
                Text(
                    text = day,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            // Empty slots
            items(emptySlots.size) {
                Spacer(modifier = Modifier.aspectRatio(1f))
            }

            // Days
            items(days) { day ->
                val date = currentMonth.atDay(day)
                val epochMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                
                val hasTraining = trainings.any { 
                    val tDate = Instant.ofEpochMilli(it.date).atZone(ZoneId.systemDefault()).toLocalDate()
                    tDate == date
                }
                val hasCompetition = competitions.any {
                    val cDate = Instant.ofEpochMilli(it.date).atZone(ZoneId.systemDefault()).toLocalDate()
                    cDate == date
                }

                DayCell(
                    day = day,
                    hasTraining = hasTraining,
                    hasCompetition = hasCompetition,
                    onClick = { onDateSelected(epochMillis) }
                )
            }
        }
        
        FloatingActionButton(
            onClick = { onAddEntry(System.currentTimeMillis()) },
            modifier = Modifier.padding(16.dp).align(Alignment.End)
        ) {
            Text("+")
        }
    }
}

@Composable
fun DayCell(
    day: Int,
    hasTraining: Boolean,
    hasCompetition: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = day.toString())
            Row {
                if (hasTraining) {
                    Box(modifier = Modifier.size(6.dp).background(Color.Green, shape = MaterialTheme.shapes.extraSmall))
                }
                if (hasCompetition) {
                    Spacer(modifier = Modifier.width(2.dp))
                    Box(modifier = Modifier.size(6.dp).background(Color.Red, shape = MaterialTheme.shapes.extraSmall))
                }
            }
        }
    }
}
