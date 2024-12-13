package com.mrtnmrls.devhub.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mrtnmrls.devhub.R
import com.mrtnmrls.devhub.presentation.ui.theme.DevhubTheme

@Composable
internal fun LoadingLottieView(modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading_lottie_animation
        )
    )
    val preloaderProgress by animateLottieCompositionAsState(
        composition = preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LottieAnimation(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            composition = preloaderLottieComposition,
            progress = preloaderProgress
        )
    }
}

@Preview
@Composable
private fun PreviewLoadingLottieView() {
    DevhubTheme {
        Surface {
            LoadingLottieView()
        }
    }
}
