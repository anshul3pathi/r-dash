package com.example.rdash.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rdash.data.api.DesignFilesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val api: DesignFilesApi) : ViewModel() {
    private val _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { HomeScreenState.Loading }
            val response = api.fetchDesignFiles()
            _state.update { response.toUi() }
        }
    }

    fun onClickExpandSectionIcon(sectionTitle: String) {
        val state = _state.value
        if (state !is HomeScreenState.Success) return

        _state.update {
            val successState = (it as HomeScreenState.Success)
            successState.copy(
                fileSections = successState.fileSections.map { fileSection ->
                    if (fileSection.sectionTitle == sectionTitle) {
                        fileSection.copy(isExpanded = !fileSection.isExpanded)
                    } else fileSection
                }
            )
        }
    }
}