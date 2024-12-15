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

package com.example.timetable_app.screens.settings

import com.example.timetable_app.LOGIN_SCREEN
import com.example.timetable_app.SPLASH_SCREEN
import com.example.timetable_app.model.service.AccountService
import com.example.timetable_app.model.service.LogService
import com.example.timetable_app.model.service.StorageService
import com.example.timetable_app.screens.TimetableAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class SettingsViewModel @Inject constructor(
  logService: LogService,
  private val accountService: AccountService,
  private val storageService: StorageService
) : TimetableAppViewModel(logService) {
  val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous) }

  fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)


  fun onSignOutClick(restartApp: (String) -> Unit) {
    launchCatching {
      accountService.signOut()
      restartApp(SPLASH_SCREEN)
    }
  }

  fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
    launchCatching {
      accountService.deleteAccount()
      restartApp(SPLASH_SCREEN)
    }
  }
}
