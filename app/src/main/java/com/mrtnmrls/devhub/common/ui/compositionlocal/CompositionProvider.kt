package com.mrtnmrls.devhub.common.ui.compositionlocal

import androidx.activity.ComponentActivity
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}

val LocalActivity = compositionLocalOf<ComponentActivity> {
    error("No component activity found")
}