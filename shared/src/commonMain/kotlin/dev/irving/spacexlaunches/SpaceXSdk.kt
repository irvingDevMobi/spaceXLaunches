package dev.irving.spacexlaunches

import dev.irving.spacexlaunches.cache.Database
import dev.irving.spacexlaunches.cache.DatabaseDriverFactory
import dev.irving.spacexlaunches.entity.RocketLaunch
import dev.irving.spacexlaunches.network.SpaceXApi

class SpaceXSdk(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getLaunches(
        forceReload: Boolean
    ): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) cachedLaunches
        else api.getAllLaunches().also {
            database.clearDatabase()
            database.createLaunches(it)
        }
    }
}
