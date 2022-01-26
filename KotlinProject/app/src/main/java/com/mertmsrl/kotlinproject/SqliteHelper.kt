package com.mertmsrl.kotlinproject

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class SqliteHelper(
    context: Context?,
    name: String?,
    version: Int,
    openParams: SQLiteDatabase.OpenParams
) : SQLiteOpenHelper(context, name, version, openParams) {
    override fun onCreate(db: SQLiteDatabase?) {
        val tableName = "tablo"
        val query = ("CREATE TABLE IF NOT EXISTS $tableName")
        if (db != null)
            db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS tablo"
        if (db != null) {
            db.execSQL(query)
        }
    }
}