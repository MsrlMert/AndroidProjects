package com.mertmsrl.kotlinproject

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DataSource(var sqliteDb: SQLiteDatabase?, var sqliteHelper: SqliteHelper?) {



    fun openConn(){
        sqliteDb = sqliteHelper?.writableDatabase
    }

    fun closeConn(){
        sqliteHelper?.close()
    }

    fun addToBookDb(books: Books){
        openConn()
        var contentValues : ContentValues = ContentValues()
        contentValues.put("bookName","text" )
        contentValues.put("author","text" )
        sqliteDb?.insert("table", null, contentValues)

        sqliteDb?.let {
            it.insert("table", null, contentValues)
        }
        closeConn()
    }

    fun getAllData() : List<Books>{
        openConn()
        var list : ArrayList<Books> = ArrayList<Books>()
        var cursor : Cursor? = sqliteDb?.query("table", null, null, null, null, null, null)

        if (cursor != null) {
            while (!cursor.isLast ){
                var id = cursor.getInt(0)
                var bookName = cursor.getString(1)
                var bookAuthor = cursor.getString(2)
                var book : Books = Books(bookName, id, bookAuthor)
                list.add(book)

            }



        }
        closeConn()
        return list
    }

    fun deleteBook(book : Books){
        openConn()
        var id : Int = 2
        var bookName : String = book.let {
            it.bookName
        }
        var bookAuthor : String = book.author

        sqliteDb?.let {
          it.delete("table", "id = $id", null)
        }
        closeConn()

    }
}