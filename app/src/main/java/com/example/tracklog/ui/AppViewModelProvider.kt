package com.example.tracklog.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tracklog.TrackLogApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TrackLogViewModel(trackLogApplication().container.trackLogDao)
        }
    }
}

fun CreationExtras.trackLogApplication(): TrackLogApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TrackLogApplication)
