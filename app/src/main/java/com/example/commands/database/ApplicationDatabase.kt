package com.example.commands.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.commands.models.Command


@Database(entities = [Command::class], version = 1)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract fun commandDao(): CommandDao

    companion object {
        val DATABASE_NAME: String = "cmd.db"
    }
}