package com.example.rdash.ui.screens.home

import androidx.annotation.DrawableRes
import com.example.rdash.R
import com.example.rdash.data.dto.DesignFilesResponse
import com.example.rdash.data.dto.FileResponse
import com.example.rdash.utils.toDateIn_d_MMM_yy
import com.example.rdash.utils.toTimeIn_hh_mm_a

sealed class HomeScreenState {
    data object Loading : HomeScreenState()

    data class Success(
        val title: String,
        val clientName: String,
        val jobId: String,
        val fileSections: List<FileSectionUiState>
    ) : HomeScreenState()

    data object Error : HomeScreenState()
}

data class FileSectionUiState(
    val isExpanded: Boolean,
    val sectionTitle: String,
    val files: List<FileUiState>,
    val numberOfFiles: Int = files.size
)

data class FileUiState(
    val id: String,
    val name: String,
    val version: Int,
    val dateOfUpload: String,
    val timeOfUpload: String,
    val fileType: FileUiType?,
    val fileDownloadProgress: Float? = null,
    val fileUrl: String? = null
)

enum class FileUiType(
    @DrawableRes val icon: Int,
    val label: String
) {
    IMAGE(R.drawable.ic_dwg, "IMAGE"),
    DOC(R.drawable.ic_pdf, "DOC")
}

fun DesignFilesResponse.toUi(): HomeScreenState.Success {
    return HomeScreenState.Success(
        title = "Bridgestone",
        clientName = "91Squarefeet",
        jobId = "BRID1337",
        fileSections = files.groupBy {
            it.section
        }.map { (sectionName, files) ->
            FileSectionUiState(
                isExpanded = false,
                sectionTitle =  sectionName,
                files = files.map { it.toFileUiState() },
                numberOfFiles = files.size
            )
        }
    )
}

fun FileResponse.toFileUiState(fileDownloadProgress: Float? = null): FileUiState {
    return FileUiState(
        id = id,
        name = name,
        version = version,
        dateOfUpload = uploadedAt.toDateIn_d_MMM_yy() ?: "",
        timeOfUpload = uploadedAt.toTimeIn_hh_mm_a() ?: "",
        fileType = fileType.getFileUiType(),
        fileDownloadProgress = fileDownloadProgress,
        fileUrl = fileUrl
    )
}

fun String.getFileUiType(): FileUiType? {
    return FileUiType.entries.find { it.label == this }
}

