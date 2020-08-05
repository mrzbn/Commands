package com.example.commands.repositories

import com.example.commands.database.CommandDao
import com.example.commands.models.Command
import com.example.commands.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class CommandRepository(private val commandDao: CommandDao) {

    fun getCommands(): Flow<DataState<List<Command>>> = flow {
        emit(DataState.Loading)
        // for better understanding purpose
        delay(1000)
        try {
            val commands = commandDao.getAll()
            emit(DataState.Success(commands))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun saveCommand(command: Command): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        // for better understanding purpose
        delay(1000)
        try {
            commandDao.insert(command = command)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}