/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.timetable_app.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timetable_app.R.string as AppText
import com.example.timetable_app.common.composable.*
import com.example.timetable_app.common.ext.basicButton
import com.example.timetable_app.common.ext.fieldModifier
import com.example.timetable_app.common.ext.textButton
import com.example.timetable_app.ui.theme.AppTheme as TimetableAppTheme
import com.example.timetable_app.common.composable.BasicButton
import com.example.timetable_app.common.composable.BasicTextButton
import com.example.timetable_app.common.composable.BasicToolbar
import com.example.timetable_app.common.composable.EmailField
import com.example.timetable_app.common.composable.PasswordField

@Composable
fun LoginScreen(
  openAndPopUp: (String, String) -> Unit,
  viewModel: LoginViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState

  LoginScreenContent(
    uiState = uiState,
    onEmailChange = viewModel::onEmailChange,
    onPasswordChange = viewModel::onPasswordChange,
    onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
    onForgotPasswordClick = viewModel::onForgotPasswordClick
  )
}

@Composable
fun LoginScreenContent(
  modifier: Modifier = Modifier,
  uiState: LoginUiState,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onSignInClick: () -> Unit,
  onForgotPasswordClick: () -> Unit
) {
  BasicToolbar(AppText.login_details)

  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    EmailField(uiState.email, onEmailChange, Modifier.fieldModifier())
    PasswordField(uiState.password, onPasswordChange, Modifier.fieldModifier())

    BasicButton(AppText.sign_in, Modifier.basicButton()) { onSignInClick() }

    BasicTextButton(AppText.forgot_password, Modifier.textButton()) {
      onForgotPasswordClick()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
  val uiState = LoginUiState(
    email = "email@test.com"
  )

  TimetableAppTheme {
    LoginScreenContent(
      uiState = uiState,
      onEmailChange = { },
      onPasswordChange = { },
      onSignInClick = { },
      onForgotPasswordClick = { }
    )
  }
}
