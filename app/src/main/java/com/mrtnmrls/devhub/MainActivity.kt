package com.mrtnmrls.devhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalActivity
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalNavController
import com.mrtnmrls.devhub.common.ui.view.DevHubContainer
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            DevhubTheme(
                dynamicColor = false
            ) {
                CompositionLocalProvider(
                    LocalNavController provides navController,
                    LocalActivity provides this@MainActivity
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DevHubContainer()
                    }
                }
            }
        }
    }
}
