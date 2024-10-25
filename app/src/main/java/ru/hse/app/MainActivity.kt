package ru.hse.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.hse.app.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                NotesApp()
            }
        }
    }
}

@Composable
fun NotesApp() {
    var notes by remember { mutableStateOf(listOf<String>()) }
    var isAddingNote by remember { mutableStateOf(false) }
    var currentNote by remember { mutableStateOf("") }

    if (isAddingNote) {
        AddNoteScreen(
            noteText = currentNote,
            onNoteChange = { currentNote = it },
            onSaveNote = {
                if (it.isNotBlank()) {
                    notes = notes + it
                }
                isAddingNote = false
                currentNote = ""
            },
            onCancel = {
                isAddingNote = false
                currentNote = ""
            }
        )
    } else {
        NoteListScreen(
            notes = notes,
            onAddNote = { isAddingNote = true },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    notes: List<String>,
    onAddNote: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить заметку") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNote) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить заметку")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            items(notes) { note ->
                Text(
                    text = note,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    noteText: String,
    onNoteChange: (String) -> Unit,
    onSaveNote: (String) -> Unit,
    onCancel: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Note") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TextField(
                value = noteText,
                onValueChange = onNoteChange,
                label = { Text("Enter note") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = { onSaveNote(noteText) }) {
                    Text("Сохранить")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onCancel) {
                    Text("Отменить")
                }
            }
        }
    }
}