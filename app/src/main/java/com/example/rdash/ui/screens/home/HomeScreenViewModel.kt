package com.example.rdash.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rdash.data.api.DesignFilesApi
import com.example.rdash.data.file_downloader.FileDownloader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val api: DesignFilesApi,
    private val fileDownloader: FileDownloader
) : ViewModel() {
    private val _state: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun onClickFile(fileId: String, fileUrl: String) {
        if (_state.value !is HomeScreenState.Success) return

        val oldUiState = state.value as HomeScreenState.Success
        fileDownloader.download(fileUrl)
            .onEach { progress ->
                progress?.let {
                    _state.update {
                        HomeScreenState.Success(
                            title = "Bridgestone",
                            clientName = "91Squarefeet",
                            jobId = "BRID1337",
                            fileSections = oldUiState.fileSections.map { fileSection ->
                                fileSection.copy(
                                    files = fileSection.files.map { file ->
                                        if (file.id == fileId) file.copy(fileDownloadProgress = progress)
                                        else file
                                    }
                                )

                            }
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}