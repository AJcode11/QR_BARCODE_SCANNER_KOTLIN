package com.ajindustries.scanit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface BookDao {

    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Query("DELETE  FROM books")
    fun deleteBook()

    @Query("SELECT * FROM books")
    fun getAllBooks() :List<BookEntity>

    @Query("SELECT * FROM books WHERE enrollno = :enrollno")
    fun getBookById(enrollno: String):BookEntity

}