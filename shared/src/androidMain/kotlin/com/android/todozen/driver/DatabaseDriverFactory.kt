package com.android.todozen.driver

import android.content.Context
import com.android.todozen.TaskDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TaskDatabase.Schema, context, "task.db")
    }
}