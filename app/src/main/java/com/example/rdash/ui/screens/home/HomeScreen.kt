package com.example.rdash.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rdash.R
import com.example.rdash.ui.screens.home.components.FileItem
import com.example.rdash.ui.screens.home.components.FileSectionItem
import com.example.rdash.ui.screens.home.components.HomeScreenTopBar
import com.example.rdash.ui.theme.RDashTheme

@Composable
fun HomeScreenRoute(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    HomeScreen(state = state, onClickExpandIcon = viewModel::onClickExpandSectionIcon)
}

@Composable
private fun HomeScreen(
    state: HomeScreenState,
    onClickExpandIcon: (sectionTitle: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            HomeScreenState.Error -> Box(modifier = Modifier.fillMaxSize())
            HomeScreenState.Loading -> HomeScreen_Loading(modifier = Modifier.fillMaxSize())
            is HomeScreenState.Success -> HomeScreen_Success(
                state = state,
                onClickExpandIcon = onClickExpandIcon
            )
        }
    }
}

@Composable
fun HomeScreen_Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimary,
            trackColor = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun BoxScope.HomeScreen_Success(
    state: HomeScreenState.Success,
    onClickExpandIcon: (sectionTitle: String) -> Unit,
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
                        onClickShowMoreIcon = { onClickExpandIcon(fileSection.sectionTitle) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (fileSection.isExpanded) {
                    items(items = fileSection.files) { file ->
                        FileItem(
                            iconRes = file.fileType?.icon ?: R.drawable.ic_launcher_foreground,
                            fileName = file.name,
                            fileVersion = file.version,
                            uploadDate = file.dateOfUpload,
                            uploadTime = file.timeOfUpload,
                            onClick = { /*TODO*/ }
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreen_Success_Preview() {
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

    val state = HomeScreenState.Success(
        title = "Design Layouts",
        clientName = "Bridgestone",
        jobId = "BRID1337",
        fileSections = listOf(fileSection, fileSection, fileSection)
    )

    RDashTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen_Success(state = state, onClickExpandIcon = {})
            }
        }
    }
}

@Preview
@Composable
fun HomeScreen_Loading_Preview() {
    RDashTheme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                HomeScreen_Loading(modifier = Modifier.fillMaxSize())
            }
        }
    }
}