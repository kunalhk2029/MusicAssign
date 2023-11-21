package com.app.musicassign.framework.presentation.ui.song_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.app.musicassign.business.domain.model.MusicFileInfo


@OptIn(ExperimentalCoilApi::class)
@Composable
fun MusicItem(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader, musicFileInfo: MusicFileInfo,
    onItemClicked: (musicFileInfo: MusicFileInfo) -> Unit,
) {

    Row(modifier = modifier
        .clickable {
            onItemClicked.invoke(musicFileInfo)
        }
        .fillMaxWidth()
        .height(75.dp)
        .padding(5.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        val imagePainter =
            rememberImagePainter(data = "https://cms.samespace.com/assets/${musicFileInfo.cover_pic_id}",
                imageLoader = imageLoader)

        Image(painter = imagePainter, contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape), contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.width(15.dp))

        Column(modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.Start
        ) {
            Text(modifier = Modifier,
                text = musicFileInfo.music_name,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold)
            Text(text = musicFileInfo.music_artist,
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Light)
        }
    }
}