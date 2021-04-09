package com.example.tracker.viewmodels

import androidx.lifecycle.ViewModel
import com.example.tracker.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel()  {
}