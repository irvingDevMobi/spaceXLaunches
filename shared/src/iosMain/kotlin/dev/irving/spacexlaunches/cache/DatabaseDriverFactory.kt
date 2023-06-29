package dev.irving.spacexlaunches.cache

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

// TODO 12: provide actual implementations for iOS DatabaseDriverFactory
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDataBase.Schema, "test.db")
    }
}
