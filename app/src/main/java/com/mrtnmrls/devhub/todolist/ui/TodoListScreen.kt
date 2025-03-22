package com.mrtnmrls.devhub.todolist.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mrtnmrls.devhub.common.ui.compositionlocal.LocalNavController
import com.mrtnmrls.devhub.common.ui.view.DevCheckbox
import com.mrtnmrls.devhub.common.ui.view.DevTopAppBar
import com.mrtnmrls.devhub.common.ui.view.LoadingLottieView
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.common.ui.theme.Typography
import com.mrtnmrls.devhub.todolist.domain.model.Task
import com.mrtnmrls.devhub.todolist.presentation.TaskDialogState
import com.mrtnmrls.devhub.todolist.presentation.TaskEvent
import com.mrtnmrls.devhub.todolist.presentation.TaskScreenState
import com.mrtnmrls.devhub.todolist.presentation.TaskUiState
import com.mrtnmrls.devhub.todolist.presentation.TaskViewModel

@Composable
internal fun TodoListContainer() {
    val navController = LocalNavController.current
    val viewModel = hiltViewModel<TaskViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DevTopAppBar(
                title = "Todo list"
            ) { navController.navigateUp() }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.handleEvent(TaskEvent.OnShowDialog) },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
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
        isTaskDialogDisplayed = when (state.dialogState) {
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
        modifier = modifier
            .fillMaxSize()
    ) {
        items(tasks) { task ->
            TaskItem(
                task = task,
                modifier = Modifier.animateItem()
            ) { onTaskEvent(it) }
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier,
    onTaskEvent: (TaskEvent) -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onTaskEvent(TaskEvent.ToggleTask(task.uid)) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DevCheckbox(
                modifier = Modifier,
                isChecked = task.isCompleted,
                onCheckedChange = { onTaskEvent(TaskEvent.ToggleTask(task.uid)) }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AnimatedCheckedText(
                    text = task.title,
                    isChecked = task.isCompleted,
                    style = Typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                AnimatedCheckedText(
                    text = task.description,
                    isChecked = task.isCompleted,
                    style = Typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .weight(0.2f),
                onClick = { onTaskEvent(TaskEvent.DeleteTask(task.uid)) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun AnimatedCheckedText(
    text: String,
    isChecked: Boolean = false,
    style: TextStyle,
    color: Color = MaterialTheme.colorScheme.primary,
    animationDurationMillis: Int = 1000
) {
    val progress = remember { Animatable(if (isChecked) 1f else 0f) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    LaunchedEffect(isChecked) {
        progress.animateTo(
            targetValue = if (isChecked) 1f else 0f,
            animationSpec = tween(durationMillis = animationDurationMillis)
        )
    }

    Text(
        text = text,
        style = style,
        color = color,
        onTextLayout = { textLayoutResult = it },
        modifier = Modifier
            .drawWithContent {
            drawContent()

            textLayoutResult?.let { layoutResult ->
                val lineCount = layoutResult.lineCount
                for (i in 0 until lineCount) {
                    val lineStart = layoutResult.getLineLeft(i)
                    val lineEnd = layoutResult.getLineRight(i)
                    val lineWidth = lineEnd - lineStart
                    val animatedWidth = lineWidth * progress.value
                    val linePositionY = layoutResult.getLineTop(i) + (layoutResult.getLineBottom(i) - layoutResult.getLineTop(i)) / 2

                    drawLine(
                        color = color,
                        start = Offset(0f, linePositionY),
                        end = Offset(animatedWidth, linePositionY),
                        strokeWidth = 4f
                    )
                }
            }
        }
    )
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
                    ),
                    Task(
                        uid = "12391293",
                        title = "A long task title, this can be a problem with this design",
                        description = "Or maybe not, we need to see when the user taps \"Add task\" \n\nLet's see!",
                        isCompleted = false
                    )
                )
            ) { }
        }
    }
}


