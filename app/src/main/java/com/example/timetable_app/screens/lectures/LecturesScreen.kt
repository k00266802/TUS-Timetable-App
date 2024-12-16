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

package com.example.timetable_app.screens.lectures

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.timetable_app.R.drawable as AppIcon
import com.example.timetable_app.R.string as AppText
import com.example.timetable_app.common.composable.ActionToolbar
import com.example.timetable_app.common.ext.smallSpacer
import com.example.timetable_app.common.ext.toolbarActions
import com.example.timetable_app.model.Lecture
import com.example.timetable_app.ui.theme.AppTheme as TimetableAppTheme

@Composable
@ExperimentalMaterialApi
fun TasksScreen(
  openScreen: (String) -> Unit,
  viewModel: LecturesViewModel = hiltViewModel()
) {
  val lectures = viewModel.lectures.collectAsStateWithLifecycle(emptyList())
  val options by viewModel.options

  TasksScreenContent(
    lectures = lectures.value,
    options = options,
    onAddClick = viewModel::onAddClick,
    onSettingsClick = viewModel::onSettingsClick,
    openScreen = openScreen
  )

  LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun TasksScreenContent(
  modifier: Modifier = Modifier,
  lectures: List<Lecture>,
  options: List<String>,
  onAddClick: ((String) -> Unit) -> Unit,
  onSettingsClick: ((String) -> Unit) -> Unit,
  openScreen: (String) -> Unit
) {
  Scaffold(
//    floatingActionButton = {
//      FloatingActionButton(
//        onClick = { onAddClick(openScreen) },
//        backgroundColor = MaterialTheme.colors.primary,
//        contentColor = MaterialTheme.colors.onPrimary,
//        modifier = modifier.padding(16.dp)
//      ) {
//        Icon(Icons.Filled.Add, "Add")
//      }
//    }
  ) {
    Column(modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()) {
      ActionToolbar(
        title = AppText.lectures,
        modifier = Modifier.toolbarActions(),
        primaryActionIcon = AppIcon.ic_settings,
        primaryAction = { onSettingsClick(openScreen) }
      )

      Spacer(modifier = Modifier.smallSpacer())
      val expandStates = remember(lectures) { lectures.map { false }.toMutableStateList() }
      Log.w("expandStates", expandStates.toString())

      LazyColumn {
        lectures.forEachIndexed { i, lecture ->
          val expanded = expandStates[i]
          item(key = i){
            LectureItem(
              lecture = lecture,
              options = options,
              isExpanded = expanded,
              onExpandedChange = { expandStates[i] = it }
            )
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@ExperimentalMaterialApi
@Composable
fun TasksScreenPreview() {
  val lecture = Lecture(
    lectureName = "Lecture title",
    description = "Lecture Description"
  )

  val options = TaskActionOption.Companion.getOptions(hasEditOption = true)

  TimetableAppTheme {
    TasksScreenContent(
      lectures = listOf(lecture),
      options = options,
      onAddClick = { },
      onSettingsClick = { },
      openScreen = { }
    )
  }
}