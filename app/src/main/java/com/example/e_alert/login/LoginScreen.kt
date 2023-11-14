package com.example.e_alert.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.e_alert.barangayList

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToSignUpPage:() -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "LOGIN",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )
        if (isError){
            Text(
                text = loginUiState?.loginError ?: "Unknown Error",
                color = Color.Red,
            )
        }

        //Email
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.email ?: "",
            onValueChange = {loginViewModel?.onEmailChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            label = { Text(text = "Email") },
            isError = isError
        )

        //password
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.password ?: "",
            onValueChange = {loginViewModel?.onPasswordChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null)
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )

        Button(onClick = { loginViewModel?.loginUser(context) }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account?"
            )
            TextButton(onClick = { onNavToSignUpPage.invoke() }) {
                Text(text = "Sign Up")
            }
        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage:() -> Unit,
    onNavToLoginPage:() -> Unit
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    Scaffold (
        topBar = { TopBarSignUp() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (isError){
                Text(
                    text = loginUiState?.signUpError ?: "Unknown Error",
                    color = Color.Red,
                )
            }

            /*TODO: Profile Photo selector goes here*/

            //Sign Up email field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = shapes.small,
                value = loginUiState?.emailSignUp ?: "",
                onValueChange = {loginViewModel?.onEmailSignUpChange(it)},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = "Email")
                },
                isError = isError
            )

            //Sign up password field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = shapes.small,
                value = loginUiState?.passwordSignUp ?: "",
                onValueChange = {loginViewModel?.onPasswordSignUpChange(it)},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = null)
                },
                label = {
                    Text(text = "Password")
                },
                visualTransformation = PasswordVisualTransformation(),
                isError = isError
            )

            // Confirm Password
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = shapes.small,
                value = loginUiState?.confirmPasswordSignUp ?: "",
                onValueChange = {loginViewModel?.onConfirmPasswordChange(it)},
                label = { Text(text = "Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                isError = isError
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Personal Info")

            //First Name
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = shapes.small,
                value = loginUiState?.firstName ?: "",
                onValueChange = {loginViewModel?.onFirstNameChange(it)},
                label = { Text(text = "First Name") },
                supportingText = { Text(text = "*Required") },
                isError = isError
            )

            //Last Name
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = shapes.small,
                value = loginUiState?.lastName ?: "",
                onValueChange = {loginViewModel?.onLastNameChange(it)},
                label = { Text(text = "Last Name") },
                supportingText = { Text(text = "*Required") },
                isError = isError
            )

            //Street
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = shapes.small,
                value = loginUiState?.street ?: "",
                onValueChange = {loginViewModel?.onStreetChange(it)},
                label = { Text(text = "Street") },
                isError = isError
            )

            var isExpanded by remember {
                mutableStateOf(false)
            }

            var baranggay by remember {
                mutableStateOf("")
            }

            //Baranggay
            ExposedDropdownMenuBox(
                expanded = isExpanded ,
                onExpandedChange = {isExpanded = it}
            ) {
                OutlinedTextField(
                    value = baranggay,
                    onValueChange = { loginViewModel?.onBaranggayChange(baranggay) },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults
                            .TrailingIcon(expanded = isExpanded)
                    },
                    label = { Text(text = "Baranggay") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = shapes.small
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    barangayList.forEach { baranggayItem ->
                        DropdownMenuItem(
                            text = { Text(text = baranggayItem) },
                            onClick = {
                                baranggay = baranggayItem
                                isExpanded = false
                            }
                        )
                    }
                }
            } //ExposedDropdownMenuBox

            //Phone Number
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = shapes.small,
                value = loginUiState?.phoneNumber ?: "",
                onValueChange = {loginViewModel?.onPhoneNumberChange(it)},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Phone,
                        contentDescription = null)
                },
                prefix = { Text(text = "(+63)") },
                label = { Text(text = "Phone") },
                isError = isError
            )

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = shapes.small,
                onClick = { loginViewModel?.createUser(context) }
            ) {
                Text(text = "Sign Up")
            }

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = { onNavToLoginPage.invoke() }) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Have an account? Sign In here!",
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))

            if (loginUiState?.isLoading == true){
                CircularProgressIndicator()
            }

            LaunchedEffect(key1 = loginViewModel?.hasUser){
                if (loginViewModel?.hasUser == true){
                    onNavToHomePage.invoke()
                }
            }
        } //Column
    } //Scaffold
} //SignUpScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSignUp () {
    TopAppBar(
        title = { Text(text = "Sign Up") },
        navigationIcon = {
            IconButton(onClick = { /*TODO: Navigate to LoginScreen*/ }
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