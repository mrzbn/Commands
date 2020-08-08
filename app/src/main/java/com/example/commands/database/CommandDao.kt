package com.example.commands.database

import androidx.room.*
import com.example.commands.models.Command

@Dao
public interface CommandDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(command: Command): Long

    @Query("SELECT * FROM command")
    suspend fun getAll(): List<Command>

    @Delete
    suspend fun delete(command: Command)

}