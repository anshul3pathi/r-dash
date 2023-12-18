package com.example.rdash.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rdash.R
import com.example.rdash.ui.theme.GrayEBEB
import com.example.rdash.ui.theme.RDashTheme

@Composable
fun FileSectionItem(
    sectionTitle: String,
    numberOfFiles: Int,
    isSectionExpanded: Boolean,
    onClickShowMoreIcon: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sectionTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = stringResource(
                    id = R.string.format_number_files,
                    numberOfFiles
                ),
                modifier = Modifier
                    .background(color = GrayEBEB, shape = RoundedCornerShape(3.dp))
                    .padding(2.dp)
            )
        }

        IconButton(onClick = onClickShowMoreIcon) {
            Icon(
                imageVector = if (isSectionExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun FileSectionItem_Preview() {
    RDashTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            FileSectionItem(
                sectionTitle = "2D Layout/Adaptation",
                numberOfFiles = 5,
                isSectionExpanded = true,
                onClickShowMoreIcon = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}