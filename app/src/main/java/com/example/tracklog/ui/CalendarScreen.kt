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
        // Monday start: Monday=1 -> 0, Sunday=7 -> 6
        val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value - 1
        
        val days = (1..daysInMonth).toList()
        val emptySlots = (0 until firstDayOfMonth).toList()

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.weight(1f)
        ) {
            // Weekday headers (Monday start)
            items(listOf("M", "T", "W", "T", "F", "S", "S")) { day ->
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
                
                val competition = competitions.find {
                    val cDate = Instant.ofEpochMilli(it.date).atZone(ZoneId.systemDefault()).toLocalDate()
                    cDate == date
                }
                
                val hasTraining = trainings.any { 
                    val tDate = Instant.ofEpochMilli(it.date).atZone(ZoneId.systemDefault()).toLocalDate()
                    tDate == date
                }

                DayCell(
                    day = day,
                    hasTraining = hasTraining,
                    competition = competition,
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
    competition: Competition?,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        hasTraining -> Color(0xFF90EE90) // Light Green
        competition != null -> {
            if (competition.location == "AL") {
                Color(0xFFFFCCCB) // Light Red
            } else {
                Color.Cyan // Cyan for PC
            }
        }
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(backgroundColor, shape = MaterialTheme.shapes.small)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.toString(),
                color = if (hasTraining || competition != null) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
