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

    suspend fun getCommands(): Flow<DataState<List<Command>>> = flow {
        emit(DataState.Loading)
        //
        delay(1000)
        try {
            val commands = commandDao.getAll()
            emit(DataState.Success(commands))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}