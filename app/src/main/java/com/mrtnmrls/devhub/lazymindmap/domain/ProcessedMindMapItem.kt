package com.mrtnmrls.devhub.lazymindmap.domain

import androidx.compose.ui.layout.Placeable

data class ProcessedMindMapItem(
    val placeable: Placeable,
    val finalXPosition: Int,
    val finalYPosition: Int
)
