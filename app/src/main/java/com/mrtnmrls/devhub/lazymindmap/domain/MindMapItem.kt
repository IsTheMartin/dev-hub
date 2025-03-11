package com.mrtnmrls.devhub.lazymindmap.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Constraints

data class MindMapItem(
    val constraints: Constraints,
    val offset: Offset,
    val content: @Composable () -> Unit
)
