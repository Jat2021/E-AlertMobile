package com.example.e_alert.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onNavToHomePage:() -> Unit,
    onNavToSignUpPage:() -> Unit
) {
    val loginUiState = loginViewModel.loginUiState
    val isError = loginUiState.loginError != null
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), true)
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "E-Alert",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )

            if (isError) {
                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = MaterialTheme.shapes.small
                    )
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = loginUiState.loginError ?: "Something went wrong.",
                        color = MaterialTheme.colorScheme.error,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Spacer(modifier = Modifier.height(32.dp))
            }

            //Email
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                value = loginUiState.email,
                onValueChange = { loginViewModel.onEmailChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                label = { Text(text = "Email") },
                isError = isError
            )

            Spacer(modifier = Modifier.height(8.dp))

            //password
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                value = loginUiState.password,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = isError
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = { loginViewModel.loginUser(context) }
            ) {
                if (loginUiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeCap = StrokeCap.Round
                    )
                } else {
                    Text(text = "Sign In")
                }
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onClick = { onNavToSignUpPage.invoke() }
            ) {
                Text(text = "Create an account")
            }

            LaunchedEffect(key1 = loginViewModel.hasUser) {
                if (loginViewModel.hasUser) {
                    onNavToHomePage.invoke()
//                    if (loginViewModel.isAuthorizedAccount(Firebase.auth.currentUser!!.uid)) {
//
//                    }
                }
            } //LaunchedEffect

//            if (loginViewModel?.hasUser == true) {
//                if (!loginViewModel.isAuthorizedAccount(AuthRepository().getUserId())) {
//                    FirebaseAuth.getInstance().signOut()
//                    Toast.makeText(
//                        context, "Account not Allowed. This account is for Authorities only",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Emergency Contact Numbers",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Column {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "POLICE STATION 1 - 0918 464 4371"
                )

                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "POLICE STATION 2 - 0949 962 4949 1"
                )

                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "POLICE STATION 3 - 0928 484 1119"
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "POLICE STATION 4 - 0998 559 8609 0"
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "POLICE STATION 5 - 0961 452 0223"
                )

                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "POLICE STATION 6 - 0922 475 1636"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSignUp (onNavToSignInPage : () -> Unit) {
    TopAppBar(
        title = { Text(text = "Sign Up") },
        navigationIcon = {
            IconButton(onClick = { onNavToSignInPage.invoke() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        } //navigationIcon
    ) //TopAppBar
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLogin () {
    TopAppBar(title = { Text(text = "Login") })
}