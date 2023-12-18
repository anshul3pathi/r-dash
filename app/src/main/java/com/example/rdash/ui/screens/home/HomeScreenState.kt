package com.example.rdash.ui.screens.home

import androidx.annotation.DrawableRes
import com.example.rdash.R

data class HomeScreenUiState(
    val title: String,
    val clientName: String,
    val jobId: String,
    val fileSections: List<FileSectionUiState>
)

data class FileSectionUiState(
    val isExpanded: Boolean,
    val sectionTitle: String,
    val files: List<FileUiState>,
    val numberOfFiles: Int = files.size
)

data class FileUiState(
    val name: String,
    val version: Int,
    val dateOfUpload: String,
    val timeOfUpload: String,
    val fileType: FileUiType
)

enum class FileUiType(@DrawableRes val icon: Int) {
    // TODO -> provide icon
    IMAGE(R.drawable.ic_launcher_foreground),
    DOC(R.drawable.ic_launcher_foreground)
}

