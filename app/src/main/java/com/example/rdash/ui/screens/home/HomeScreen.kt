package com.example.rdash.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rdash.ui.screens.home.components.FileItem
import com.example.rdash.ui.screens.home.components.FileSectionItem
import com.example.rdash.ui.screens.home.components.HomeScreenTopBar
import com.example.rdash.ui.theme.RDashTheme

@Composable
fun HomeScreen(
    state: HomeScreenUiState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            HomeScreenTopBar(
                title = state.title,
                clientName = state.clientName,
                jobId = state.jobId
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            state.fileSections.forEach { fileSection ->
                item {
                    FileSectionItem(
                        sectionTitle = fileSection.sectionTitle,
                        numberOfFiles = fileSection.numberOfFiles,
                        isSectionExpanded = fileSection.isExpanded,
                        onClickShowMoreIcon = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                items(items = fileSection.files) { file ->
                    FileItem(
                        iconRes = file.fileType.icon,
                        fileName = file.name,
                        fileVersion = file.version,
                        uploadDate = file.dateOfUpload,
                        uploadTime = file.timeOfUpload,
                        onClick = { /*TODO*/ })

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreen_Preview() {
    val file = FileUiState(
        name = "2D Electrical wiring plan",
        version = 3,
        dateOfUpload = "12 Nov, 21",
        timeOfUpload = "02:58 PM",
        fileType = FileUiType.DOC
    )

    val fileSection = FileSectionUiState(
        isExpanded = false,
        sectionTitle = "2D Layout/Adaptation",
        files = listOf(file, file, file)
    )

    val state = HomeScreenUiState(
        title = "Design Layouts",
        clientName = "Bridgestone",
        jobId = "BRID1337",
        fileSections = listOf(fileSection, fileSection, fileSection)
    )

    RDashTheme {
        Surface {
            HomeScreen(state = state)
        }
    }
}