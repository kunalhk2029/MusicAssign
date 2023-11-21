package com.app.musicassign.framework.presentation.ui.song_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.musicassign.R

data class TabItem(
    val title:String,
    val selectedIcon  :Int= R.drawable.ic_baseline_fiber_manual_record_24
)
val tabItemsList = listOf(
    TabItem(title = "For You"),
    TabItem(title = "Top Tracks")
)