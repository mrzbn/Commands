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

    private val _dataState: MutableLiveData<DataState<List<Command>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Command>>>
        get() = _dataState

    fun setStateEvent(commandsStateEvent: CommandsStateEvent){
        viewModelScope.launch {
            when(commandsStateEvent){
                is CommandsStateEvent.GetCommandsEvent -> {
                    repository.getCommands()
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }
                        .collect { }
                }

                CommandsStateEvent.None -> {
                    // none
                }
            }
        }
    }
}

sealed class CommandsStateEvent{

    object GetCommandsEvent: CommandsStateEvent()

    object None: CommandsStateEvent()
}