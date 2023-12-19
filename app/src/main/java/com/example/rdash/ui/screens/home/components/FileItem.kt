package com.example.rdash.ui.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rdash.R
import com.example.rdash.ui.theme.GrayE4E7EC
import com.example.rdash.ui.theme.GrayEBEB
import com.example.rdash.ui.theme.Green47A4AA
import com.example.rdash.ui.theme.RDashTheme
import com.example.rdash.ui.theme.RedFFE3E4

@Composable
fun FileItem(
    @DrawableRes iconRes: Int,
    fileName: String,
    fileVersion: Int,
    uploadDate: String,
    uploadTime: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fileDownloadProgress: Float? = null
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .border(width = 1.dp, color = GrayE4E7EC, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(height = 30.dp, width = 24.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column(modifier.weight(1f)) {
            Text(text = fileName, fontWeight = FontWeight.Bold, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(2.dp))

            Row {
                Text(
                    text = stringResource(id = R.string.format_v_number, fileVersion),
                    fontSize = 8.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = RedFFE3E4, shape = RoundedCornerShape(2.dp))
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = stringResource(id = R.string.format_upload_time, uploadDate, uploadTime),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        fileDownloadProgress?.let {
            FileDownloadIndicator(progress = it)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Image(painter = painterResource(R.drawable.ic_more_horizontal), contentDescription = null)
    }
}

@Composable
private fun FileDownloadIndicator(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val fileDownloadCompleted = progress >= 1f
    if (fileDownloadCompleted) {
        Icon(
            painter = painterResource(id = R.drawable.ic_file_downloaded),
            modifier = Modifier.size(18.dp),
            contentDescription = null,
            tint = Green47A4AA
        )
    } else {
        Box(modifier = modifier.size(18.dp)) {
            CircularProgressIndicator(
                progress = progress,
                color = Green47A4AA,
                strokeWidth = 2.dp,
                trackColor = GrayEBEB
            )

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FileItem_Preview() {
    RDashTheme {
        Surface(modifier = Modifier.padding(8.dp)) {
            FileItem(
                iconRes = R.drawable.ic_pdf,
                fileName = "2D Electrical wiring plan",
                fileVersion = 1,
                uploadDate = "12 Nov, 21",
                uploadTime = "02:58 PM",
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}