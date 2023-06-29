package dev.irving.spacexlaunches.cache

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

// TODO 11: provide actual implementations for Android DatabaseDriverFactory
actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDataBase.Schema, context, "test.db")
    }
}
