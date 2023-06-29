package dev.irving.spacexlaunches.cache

import com.squareup.sqldelight.db.SqlDriver

/*
  SQLDelight provides multiple platform-specific implementations of the SQLite driver,
  so you need to create them for each platform separately.
 */
// TODO 10: create expect DatabaseDriverFactory
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
