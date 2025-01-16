package com.mrtnmrls.devhub.todolist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mrtnmrls.devhub.presentation.common.LoadingLottieView
import com.mrtnmrls.devhub.presentation.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.todolist.domain.model.Task
import com.mrtnmrls.devhub.todolist.presentation.TaskDialogState
import com.mrtnmrls.devhub.todolist.presentation.TaskEvent
import com.mrtnmrls.devhub.todolist.presentation.TaskScreenState
import com.mrtnmrls.devhub.todolist.presentation.TaskUiState
import com.mrtnmrls.devhub.todolist.presentation.TaskViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TodoListContainer(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<TaskViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "To-do list") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.handleEvent(TaskEvent.OnShowDialog) }
            ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add task") }
        }
    ) { paddingValues ->
        TodoListScreen(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onTaskEvent = viewModel::handleEvent
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(TaskEvent.LoadTasks)
    }
}

@Composable
private fun TodoListScreen(
    modifier: Modifier = Modifier,
    state: TaskUiState,
    onTaskEvent: (TaskEvent) -> Unit
) {
    var isTaskDialogDisplayed by remember { mutableStateOf(false) }
    when (state.screenState) {
        TaskScreenState.EmptyTasks -> Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Red)
        )
        TaskScreenState.Loading -> LoadingLottieView()
        is TaskScreenState.SuccessfulTaskContent -> TasksListView(
            modifier = modifier,
            tasks = state.screenState.tasksList,
            onTaskEvent = onTaskEvent
        )
    }

    if (isTaskDialogDisplayed) {
        NewTaskDialog { onTaskEvent(it) }
    }

    LaunchedEffect(state.dialogState) {
        isTaskDialogDisplayed = when(state.dialogState) {
            TaskDialogState.Displayed -> true
            TaskDialogState.Hidden -> false
        }
    }
}

@Composable
private fun TasksListView(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskEvent: (TaskEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(tasks) { task ->
            TaskItem(task) { onTaskEvent(it) }
        }
    }
}

@Composable
private fun TaskItem(task: Task, onTaskEvent: (TaskEvent) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onTaskEvent(TaskEvent.ToggleTask(task.uid)) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                modifier = Modifier.weight(0.2f),
                checked = task.isCompleted,
                onCheckedChange = { onTaskEvent(TaskEvent.ToggleTask(task.uid)) },
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = task.title,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                Text(
                    text = task.description,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
            }
            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .weight(0.3f),
                onClick = { onTaskEvent(TaskEvent.DeleteTask(task.uid)) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task"
                )
            }
        }
    }
}

@Composable
private fun NewTaskDialog(
    onTaskEvent: (TaskEvent) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onTaskEvent(TaskEvent.OnDismissDialog) }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it }
                )
                Button(
                    onClick = {
                        onTaskEvent(
                            TaskEvent.AddTask(
                                Task(
                                    title = title,
                                    description = description
                                )
                            )
                        )
                    }
                ) {
                    Text(text = "Add task")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTaskItem() {
    DevhubTheme {
        Surface {
            TasksListView(
                modifier = Modifier,
                tasks = listOf(
                    Task(
                        "uid",
                        "My new task",
                        "Description of the task",
                        false
                    ),
                    Task(
                        "uid",
                        "My new task",
                        "Description of the task",
                        true
                    )
                )
            ) { }
        }
    }
}
