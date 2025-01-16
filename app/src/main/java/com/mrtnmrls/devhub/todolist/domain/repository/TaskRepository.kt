package com.mrtnmrls.devhub.todolist.domain.repository

import com.mrtnmrls.devhub.todolist.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasksFlow(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun deleteTask(taskUid: String)
    suspend fun updateTask(task: Task)
    suspend fun toggleTask(taskUid: String)

}
