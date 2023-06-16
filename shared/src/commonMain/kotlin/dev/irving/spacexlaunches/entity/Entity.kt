package dev.irving.spacexlaunches.entity

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunch(
    @SerialName("flight_number") val flightNumber: Int,
    @SerialName("name") val missionName: String,
    @SerialName("data_utc") val launchDateUTC: String,
    val details: String,
    @SerialName("success") val launchSuccess: Boolean,
    val links: Links
) {
    var launchYear = launchDateUTC.toInstant().toLocalDateTime(TimeZone.UTC).year
}

@Serializable
data class Links(
    val patch: Patch?,
    val article: String
)

@Serializable
data class Patch(
    val small: String?,
    val large: String?
)
