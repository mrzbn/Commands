package com.example.commands.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.commands.database.ApplicationDatabase
import com.example.commands.database.CommandDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideApplicationDatabase(@ApplicationContext context: Context): ApplicationDatabase {
        val roomDatabaseCallback: RoomDatabase.Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                db.execSQL("INSERT INTO command (howTo, line) Values ('howTo1', 'line1')")
                db.execSQL("INSERT INTO command (howTo, line) Values ('howTo2', 'line2')")
                db.execSQL("INSERT INTO command (howTo, line) Values ('howTo3', 'line3')")
            }
        }

        return Room
            .databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                ApplicationDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(roomDatabaseCallback)
            .build()
    }

    @Singleton
    @Provides
    fun provideCommandDao(applicationDatabase: ApplicationDatabase): CommandDao {
        return applicationDatabase.commandDao()
    }
}