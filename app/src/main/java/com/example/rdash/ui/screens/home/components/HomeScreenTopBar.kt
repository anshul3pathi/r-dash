package com.example.rdash.ui.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rdash.R
import com.example.rdash.ui.theme.RDashTheme

@Composable
fun HomeScreenTopBar(
    title: String,
    clientName: String,
    jobId: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 22.dp, top = 16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, bottom = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.lbl_client).toUpperCase(Locale.current),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = clientName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "|", fontSize = 12.sp)

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = stringResource(id = R.string.lbl_job_id).toUpperCase(Locale.current),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = jobId,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenTopBar_Preview() {
    RDashTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            HomeScreenTopBar(
                title = "Design Layouts",
                clientName = "Bridgestone",
                jobId = "BRID1337",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}