package com.mrtnmrls.devhub.todolist.presentation

import com.mrtnmrls.devhub.todolist.domain.model.Task

sealed class TaskEvent {
    data object LoadTasks : TaskEvent()
    data class AddTask(val task: Task) : TaskEvent()
    data class UpdateTask(val task: Task) : TaskEvent()
    data class DeleteTask(val taskUid: String) : TaskEvent()
    data class ToggleTask(val taskUid: String) : TaskEvent()
    data object OnDismissDialog : TaskEvent()
    data object OnShowDialog : TaskEvent()
}
