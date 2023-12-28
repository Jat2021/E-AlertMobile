package com.example.e_alert.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    loginViewModel : LoginViewModel,
    onNavToHomePage : () -> Unit,
    onNavToLoginPage : () -> Unit
) {
    val loginUiState = loginViewModel.loginUiState
    val isError = loginUiState.signUpError != null
    val context = LocalContext.current

    loginViewModel.retrieveBarangayListFromDB()

    Scaffold (
        topBar = { TopBarSignUp(onNavToLoginPage) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp))
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            if (isError){
                Text(
                    text = loginUiState.signUpError ?: "Unknown Error",
                    color = Color.Red,
                )
            }

            Box (
                modifier = Modifier
                    .shadow(3.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Column (modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Email & Password",
                        style = MaterialTheme.typography.titleMedium
                    )

                    //Sign Up email field
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = MaterialTheme.shapes.small,
                        value = loginUiState.emailSignUp,
                        onValueChange = { loginViewModel.onEmailSignUpChange(it) },
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
                        shape = MaterialTheme.shapes.small,
                        value = loginUiState.passwordSignUp,
                        onValueChange = { loginViewModel.onPasswordSignUpChange(it) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Lock,
                                contentDescription = null
                            )
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
                        shape = MaterialTheme.shapes.small,
                        value = loginUiState.confirmPasswordSignUp,
                        onValueChange = { loginViewModel.onConfirmPasswordChange(it) },
                        label = { Text(text = "Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = isError
                    )
                } //Column
            } //Box

            Spacer(modifier = Modifier.height(16.dp))

            Box (modifier = Modifier
                .shadow(3.dp)
                .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Personal Info",
                        style = MaterialTheme.typography.titleMedium
                    )

                    //First Name
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = MaterialTheme.shapes.small,
                        value = loginUiState.firstName,
                        onValueChange = { loginViewModel.onFirstNameChange(it) },
                        label = { Text(text = "First Name") },
                        supportingText = { Text(text = "*Required") },
                        isError = isError
                    )

                    //Last Name
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = MaterialTheme.shapes.small,
                        value = loginUiState.lastName,
                        onValueChange = { loginViewModel.onLastNameChange(it) },
                        label = { Text(text = "Last Name") },
                        supportingText = { Text(text = "*Required") },
                        isError = isError
                    )

                    //Street
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = MaterialTheme.shapes.small,
                        value = loginUiState.street,
                        onValueChange = { loginViewModel.onStreetChange(it) },
                        label = { Text(text = "Street") },
                        isError = isError
                    )

                    var isExpanded by remember {
                        mutableStateOf(false)
                    }

                    var barangay by remember {
                        mutableStateOf("")
                    }

                    //Baranggay
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = barangay,
                            onValueChange = { loginViewModel.onBarangayChange(barangay) },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults
                                    .TrailingIcon(expanded = isExpanded)
                            },
                            label = { Text(text = "Barangay") },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            shape = MaterialTheme.shapes.small
                        )

                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false }
                        ) {
                            loginViewModel.listOfBarangayState.forEach { barangayItem ->
                                DropdownMenuItem(
                                    text = { Text(text = barangayItem) },
                                    onClick = {
                                        barangay = barangayItem
                                        loginViewModel.onBarangayChange(barangay)
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
                        shape = MaterialTheme.shapes.small,
                        value = loginUiState.phoneNumber,
                        onValueChange = { loginViewModel.onPhoneNumberChange(it) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Phone,
                                contentDescription = null
                            )
                        },
                        prefix = { Text(text = "(+63)") },
                        label = { Text(text = "Phone") },
                        isError = isError
                    )
                } //Column
            } //Box

            Spacer(modifier = Modifier.size(16.dp))

            Box(
                modifier = Modifier
                    .shadow(3.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Column (modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Upload a photo of your ID (Required)",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "By uploading your ID," +
                                " you are verifying that you are a person capable" +
                                " of taking responsibility for the reports you may post in the app" +
                                " that can be viewed by the public and by the authorities managing" +
                                " and responding to such reports of a flooding or a road accident" +
                                " within the city of Naga",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    Box (modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.small
                        )
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column (modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Upload two (2) clear photos, one (1)" +
                                        " for the front and one (1) for the back of the ID." +
                                        " Make sure that the details of each side of the ID is" +
                                        " clear and visible.",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.size(8.dp))

                            Text(
                                text = "Please upload any of the following valid ID's:" +
                                        " Student ID, Driver's License, National ID, PRC ID," +
                                        " PhilPost ID, SSS ID, TIN ID.",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.size(8.dp))

                            Text(
                                text = "As much as possible, the photos'" +
                                        " format should be .png or .jpeg/.jpg",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } //Box

                    Spacer(modifier = Modifier.size(16.dp))

                    UploadIDPhoto(loginViewModel = loginViewModel)
                } //Column
            } //Box

            Spacer(modifier = Modifier.size(16.dp))

            Box (modifier = Modifier
                .shadow(3.dp)
                .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Column (modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Consent",
                        style = MaterialTheme.typography.titleMedium
                    )

                    var isChecked by remember {
                        mutableStateOf(false)
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Row {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                isChecked = checked
                            }
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        Text(
                            text = "I assure that the information I provided above are correct." +
                                    " I hereby give my consent to processing my ID I uploaded above" +
                                    " for the purpose of verifying my identity and that" +
                                    " I am a person capable of taking responsibility for any reports" +
                                    " that I may post in the app that can be viewed by the public and by" +
                                    " the authorities managing and responding to such reports of a" +
                                    " flooding or a road accident within the city of Naga.",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    } //Row

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small,
                        enabled = isChecked,
                        onClick = { loginViewModel.createUser(context) }
                    ) {
                        if (loginUiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(8.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(text = "Sign Up")
                        }
                    }

                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small,
                        onClick = { onNavToLoginPage.invoke() }
                    ) {
                        Text(text = "Already have an account")
                    }
                }
            }

            LaunchedEffect(key1 = loginViewModel.hasUser){
                if (loginViewModel.hasUser)
                    onNavToHomePage.invoke()
            }
        } //Column
    } //Scaffold
} //SignUpScreen