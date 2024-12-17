
package com.example.timetable_app.screens.campus_map

import androidx.compose.runtime.mutableStateOf
import com.example.timetable_app.CAMPUS_MAP_SCREEN
import com.example.timetable_app.EDIT_TASK_SCREEN
import com.example.timetable_app.LECTURES_SCREEN
import com.example.timetable_app.LOGIN_SCREEN
import com.example.timetable_app.SETTINGS_SCREEN
import com.example.timetable_app.model.Campus

import com.example.timetable_app.model.Lecture
import com.example.timetable_app.model.service.ConfigurationService
import com.example.timetable_app.model.service.LogService
import com.example.timetable_app.model.service.StorageService
import com.example.timetable_app.screens.TimetableAppViewModel
import com.example.timetable_app.screens.lectures.TaskActionOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class CampusScreenViewModel @Inject constructor(
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : TimetableAppViewModel(logService) {

  var campus: Campus? = Campus()

  init {
    launchCatching{
      campus = storageService.getCampus()
    }
  }

}
