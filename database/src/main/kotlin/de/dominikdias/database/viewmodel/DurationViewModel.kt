package de.dominikdias.database.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import de.dominikdias.database.data.DurationEntry
import de.dominikdias.database.repository.DurationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DurationViewModel(private val durationRepository: DurationRepository): ViewModel() {

    private var _durationList = MutableStateFlow<List<DurationEntry>>(emptyList())
    val durationList = _durationList.asStateFlow()

    init {
        viewModelScope.launch {
            durationRepository.getAllDurations().collectLatest {
                _durationList.value = it
            }
        }
    }

    fun insertDuration(duration: DurationEntry) {
        viewModelScope.launch {
            durationRepository.insertDuration(duration = duration)
        }
    }

    companion object {
        fun factory(durationRepository: DurationRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DurationViewModel(durationRepository = durationRepository)
            }
        }
    }
}