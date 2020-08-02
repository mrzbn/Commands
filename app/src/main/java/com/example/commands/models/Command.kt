package com.example.commands.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Command constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val howTo: String?,
    val line: String?)