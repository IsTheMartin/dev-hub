package com.mrtnmrls.devhub.presentation.ui.state

import com.mrtnmrls.devhub.presentation.ui.effect.LandingEffect

data class LandingState(
    val effect: LandingEffect = LandingEffect.NoOp
)
