package com.example.commands.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Command constructor(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var howTo: String?,
    var line: String?)