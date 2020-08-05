package com.example.commands.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.commands.models.Command
import com.example.commands.repositories.CommandRepository
import com.example.commands.utils.DataState
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

public class CommandsViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val repository: CommandRepository
): ViewModel() {

    val commandsState: LiveData<DataState<List<Command>>> = repository.getCommands().asLiveData()

    fun saveCommand(howTo: String, line: String): LiveData<DataState<Unit>> {
        return repository.saveCommand(Command(howTo = howTo, line = line)).asLiveData()
    }
}