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

package com.example.timetable_app.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.example.timetable_app.LOGIN_SCREEN
import com.example.timetable_app.SPLASH_SCREEN
import com.example.timetable_app.LECTURES_SCREEN
import com.example.timetable_app.model.service.AccountService
import com.example.timetable_app.model.service.ConfigurationService
import com.example.timetable_app.model.service.LogService
import com.example.timetable_app.screens.TimetableAppViewModel
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  configurationService: ConfigurationService,
  private val accountService: AccountService,
  logService: LogService
) : TimetableAppViewModel(logService) {
  val showError = mutableStateOf(false)

  init {
    launchCatching { configurationService.fetchConfiguration() }
  }

  fun onAppStart(openAndPopUp: (String, String) -> Unit) {

    showError.value = false
    openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
  }

}
