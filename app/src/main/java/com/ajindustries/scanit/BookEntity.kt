package com.ajindustries.scanit

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "books")
data class BookEntity (
    @PrimaryKey val enrollno: String =""
    )
