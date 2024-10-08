package de.dominikdias.database.viewmodel

import androidx.annotation.Keep
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import de.dominikdias.database.data.Duration
import de.dominikdias.database.interfaces.IDurationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Keep
class DatabaseViewModel(private val durationRepository: IDurationRepository): ViewModel() {

    private var _durationList = MutableStateFlow<List<Duration>>(emptyList())

    @Stable
    val durationList = _durationList.asStateFlow()

    init {
        viewModelScope.launch {
            durationRepository.getAllDurations().collectLatest {
                _durationList.value = it
            }
        }
    }

    fun insertDuration(duration: Duration) {
        viewModelScope.launch {
            durationRepository.insertDuration(duration = duration)
        }
    }

    @Keep
    companion object {
        fun factory(durationRepository: IDurationRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DatabaseViewModel(durationRepository = durationRepository)
            }
        }
    }
}
