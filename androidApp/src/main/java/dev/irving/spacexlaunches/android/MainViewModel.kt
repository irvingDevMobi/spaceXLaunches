package dev.irving.spacexlaunches.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.irving.spacexlaunches.SpaceXSdk
import dev.irving.spacexlaunches.entity.RocketLaunch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO 18: Implement ViewModel and use common repository
class MainViewModel(
    private val sdk: SpaceXSdk
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing.asStateFlow()

    private val _items = MutableStateFlow<List<RocketLaunch>>(listOf())
    val items: StateFlow<List<RocketLaunch>> get() = _items.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)
            fetchData()
        }
    }

    fun fetchData(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            println("IrvDev Refreshing....")
            _isRefreshing.update { true }
            _items.update { (sdk.getLaunches(forceRefresh)) }
            _isRefreshing.update { false }
        }
    }
}
