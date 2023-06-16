package dev.irving.spacexlaunches

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform