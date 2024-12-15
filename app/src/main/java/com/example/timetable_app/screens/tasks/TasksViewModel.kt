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

package com.example.timetable_app.screens.tasks

import androidx.compose.runtime.mutableStateOf
import com.example.timetable_app.EDIT_TASK_SCREEN
import com.example.timetable_app.SETTINGS_SCREEN
import com.example.timetable_app.STATS_SCREEN
import com.example.timetable_app.TASK_ID
import com.example.timetable_app.model.Task
import com.example.timetable_app.model.service.ConfigurationService
import com.example.timetable_app.model.service.LogService
import com.example.timetable_app.model.service.StorageService
import com.example.timetable_app.screens.TimetableAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : TimetableAppViewModel(logService) {
  val options = mutableStateOf<List<String>>(listOf())

  val tasks = storageService.tasks

  fun loadTaskOptions() {
    val hasEditOption = configurationService.isShowTaskEditButtonConfig
    options.value = TaskActionOption.Companion.getOptions(hasEditOption)
  }

  fun onTaskCheckChange(task: Task) {
    launchCatching { storageService.update(task.copy(completed = !task.completed)) }
  }

  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

  fun onStatsClick(openScreen: (String) -> Unit) = openScreen(STATS_SCREEN)

  fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
    when (TaskActionOption.Companion.getByTitle(action)) {
      TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
      TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
      TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
    }
  }

  private fun onFlagTaskClick(task: Task) {
    launchCatching { storageService.update(task.copy(flag = !task.flag)) }
  }

  private fun onDeleteTaskClick(task: Task) {
    launchCatching { storageService.delete(task.id) }
  }
}
