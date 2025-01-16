package com.mrtnmrls.devhub.todolist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrtnmrls.devhub.todolist.domain.model.Task
import com.mrtnmrls.devhub.todolist.domain.usecase.AddTaskUseCase
import com.mrtnmrls.devhub.todolist.domain.usecase.DeleteTaskUseCase
import com.mrtnmrls.devhub.todolist.domain.usecase.GetTasksUseCase
import com.mrtnmrls.devhub.todolist.domain.usecase.ToggleTaskUseCase
import com.mrtnmrls.devhub.todolist.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val toggleTaskUseCase: ToggleTaskUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskUiState())
    val state = _state.asStateFlow()

    fun handleEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.AddTask -> addTask(event.task)
            is TaskEvent.DeleteTask -> deleteTask(event.taskUid)
            TaskEvent.LoadTasks -> loadTasks()
            is TaskEvent.UpdateTask -> updateTask(event.task)
            is TaskEvent.ToggleTask -> toggleTask(event.taskUid)
            TaskEvent.OnDismissDialog -> hideTaskDialog()
            TaskEvent.OnShowDialog -> displayTaskDialog()
        }
    }

    private fun loadTasks() = viewModelScope.launch {
        getTasksUseCase()
            .collect { tasks ->
                _state.update {
                    when {
                        tasks.isEmpty() -> it.copy(screenState = TaskScreenState.EmptyTasks)
                        else -> it.copy(screenState = TaskScreenState.SuccessfulTaskContent(tasks))
                    }
                }
            }
    }

    private fun addTask(task: Task) = viewModelScope.launch {
        addTaskUseCase(task)
        hideTaskDialog()
    }

    private fun deleteTask(uid: String) = viewModelScope.launch {
        deleteTaskUseCase(uid)
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        updateTaskUseCase(task)
    }

    private fun toggleTask(uid: String) = viewModelScope.launch {
        toggleTaskUseCase(uid)
    }

    private fun displayTaskDialog() = _state.update {
        it.copy(dialogState = TaskDialogState.Displayed)
    }

    private fun hideTaskDialog() = _state.update {
        it.copy(dialogState = TaskDialogState.Hidden)
    }

}
