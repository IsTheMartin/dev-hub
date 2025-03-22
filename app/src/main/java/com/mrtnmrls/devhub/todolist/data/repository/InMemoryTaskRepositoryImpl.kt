package com.mrtnmrls.devhub.todolist.data.repository

import com.mrtnmrls.devhub.todolist.domain.model.Task
import com.mrtnmrls.devhub.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryTaskRepositoryImpl: TaskRepository {

    private val tasks = MutableStateFlow(
        listOf(
            Task(
                uid = "be312312084",
                title = "First task",
                description = "Description of task",
                isCompleted = false
            ),
            Task(
                uid = "sd12391csdi2",
                title = "Second task",
                description = "Description of task",
                isCompleted = true
            ),
            Task(
                uid = "12391293",
                title = "A long task title, this can be a problem with this design",
                description = "Or maybe not, we need to see when the user taps \"Add task\" \n\nLet's see!",
                isCompleted = false
            )
        )
    )

    override fun getTasksFlow(): Flow<List<Task>> {
        return tasks.asStateFlow()
    }

    override suspend fun addTask(task: Task) {
        tasks.value += task
    }

    override suspend fun deleteTask(taskUid: String) {
        tasks.value = tasks.value.filter { it.uid != taskUid }
    }

    override suspend fun updateTask(task: Task) {
        tasks.value = tasks.value
            .map { if (it.uid == task.uid) task else it }
    }

    override suspend fun toggleTask(taskUid: String) {
        tasks.value = tasks.value
            .map { if (it.uid == taskUid ) it.copy(isCompleted = !it.isCompleted) else it }
    }
}
