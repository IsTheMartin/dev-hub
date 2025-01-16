package com.mrtnmrls.devhub.todolist.domain.model

data class Task(
    val uid: String = "",
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false
)
