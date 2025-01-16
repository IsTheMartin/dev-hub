package com.mrtnmrls.devhub.todolist.presentation

import com.mrtnmrls.devhub.todolist.domain.model.Task

data class TaskUiState(
    val screenState: TaskScreenState = TaskScreenState.Loading,
    val dialogState: TaskDialogState = TaskDialogState.Hidden
)

sealed class TaskScreenState {
    data object Loading : TaskScreenState()
    data object EmptyTasks : TaskScreenState()
    data class SuccessfulTaskContent(val tasksList: List<Task>) : TaskScreenState()
}

sealed class TaskDialogState {
    data object Hidden : TaskDialogState()
    data object Displayed : TaskDialogState()
}
