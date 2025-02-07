package com.mrtnmrls.devhub.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.common.ui.view.LoadingLottieView
import com.mrtnmrls.devhub.login.presentation.LoginEffect
import com.mrtnmrls.devhub.login.presentation.LoginIntent
import com.mrtnmrls.devhub.landing.presentation.navigateToLandingScreen
import com.mrtnmrls.devhub.login.presentation.LoginState
import com.mrtnmrls.devhub.login.presentation.LoginScreenState
import com.mrtnmrls.devhub.common.ui.theme.Camel
import com.mrtnmrls.devhub.common.ui.theme.CetaceanBlue
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.Khaki
import com.mrtnmrls.devhub.common.ui.theme.MetallicBlue
import com.mrtnmrls.devhub.common.ui.theme.Typography
import com.mrtnmrls.devhub.login.presentation.LoginViewModel

@Composable
internal fun LoginContainer(
    navController: NavHostController
) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val state by loginViewModel.state.collectAsStateWithLifecycle()
    LoginScreen(state = state, onIntent = loginViewModel::dispatchIntent)
    HandleLoginEffects(state.effect, navController)
}

@Composable
private fun HandleLoginEffects(effect: LoginEffect, navController: NavHostController) {
    LaunchedEffect(effect) {
        when (effect) {
            LoginEffect.NoOp -> Unit
            LoginEffect.OnSuccessfulLogin -> navigateToLandingScreen(navController)
        }
    }
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
) {
    when (state.screenState) {
        LoginScreenState.Error -> LoginErrorView()
        LoginScreenState.Loading -> LoadingLottieView()
        is LoginScreenState.SuccessContent -> LoginContentView(
            onIntent = onIntent,
            uiState = state.screenState
        )
    }
}

@Composable
private fun LoginErrorView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {}
}

@Composable
private fun LoginContentView(
    modifier: Modifier = Modifier,
    onIntent: (LoginIntent) -> Unit,
    uiState: LoginScreenState.SuccessContent
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginHeaderView(
            Modifier
                .fillMaxWidth()
                .weight(0.2f)
        )

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = modifier
                .fillMaxSize()
                .weight(0.8f)
                .background(Camel)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(
                email = email,
                focusManager = focusManager,
                isMissingEmail = uiState.isMissingEmail,
            ) {
                email = it
            }
            Spacer(modifier = Modifier.height(8.dp))
            PasswordTextField(
                password = password,
                keyboardController = keyboardController,
                isMissingPassword = uiState.isMissingPassword
            ) {
                password = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onIntent(LoginIntent.OnLogin(email, password))
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLogIn,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MetallicBlue,
                    contentColor = Khaki
                ),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
private fun LoginHeaderView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MetallicBlue),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome",
                style = Typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                color = Khaki
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please enter your email and password",
                style = Typography.bodyMedium,
                color = Khaki
            )
        }
    }
}

@Composable
private fun EmailTextField(
    email: String,
    focusManager: FocusManager,
    isMissingEmail: Boolean,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = { onValueChanged(it) },
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
        isError = isMissingEmail,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Khaki,
            unfocusedLabelColor = CetaceanBlue,
            unfocusedTextColor = CetaceanBlue,
            unfocusedPlaceholderColor = CetaceanBlue,
            focusedContainerColor = Khaki,
            focusedLabelColor = CetaceanBlue,
            focusedPlaceholderColor = CetaceanBlue,
            focusedTextColor = CetaceanBlue
        )
    )
}

@Composable
private fun PasswordTextField(
    password: String,
    keyboardController: SoftwareKeyboardController?,
    isMissingPassword: Boolean,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onValueChanged(it) },
        label = { Text("Password") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        isError = isMissingPassword,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Khaki,
            unfocusedLabelColor = CetaceanBlue,
            unfocusedTextColor = CetaceanBlue,
            unfocusedPlaceholderColor = CetaceanBlue,
            focusedContainerColor = Khaki,
            focusedLabelColor = CetaceanBlue,
            focusedPlaceholderColor = CetaceanBlue,
            focusedTextColor = CetaceanBlue,
        )
    )
}

@Preview
@Composable
private fun PreviewLoginContent() {
    DevhubTheme {
        Surface {
            LoginContentView(onIntent = {}, uiState = LoginScreenState.SuccessContent())
        }
    }
}
