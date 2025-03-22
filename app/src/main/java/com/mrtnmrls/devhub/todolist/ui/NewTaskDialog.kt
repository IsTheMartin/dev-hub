package com.mrtnmrls.devhub.todolist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mrtnmrls.devhub.common.ui.view.PrimaryButton
import com.mrtnmrls.devhub.common.ui.view.VerticalSpacer
import com.mrtnmrls.devhub.common.ui.theme.DevhubTheme
import com.mrtnmrls.devhub.todolist.domain.model.Task
import com.mrtnmrls.devhub.todolist.presentation.TaskEvent

@Composable
internal fun NewTaskDialog(
    onTaskEvent: (TaskEvent) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onTaskEvent(TaskEvent.OnDismissDialog) }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(text = "Title")
                    },
                    maxLines = 1,
                    singleLine = true
                )
                VerticalSpacer(8.dp)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = { description = it },
                    placeholder = {
                        Text(text = "Description")
                    },
                    minLines = 3,
                    maxLines = 3
                )
                VerticalSpacer(8.dp)
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = "Add task"
                ) {
                    onTaskEvent(
                        TaskEvent.AddTask(
                            Task(
                                title = title,
                                description = description
                            )
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNewTaskDialog() {
    DevhubTheme {
        Surface {
            NewTaskDialog { }
        }
    }
}
