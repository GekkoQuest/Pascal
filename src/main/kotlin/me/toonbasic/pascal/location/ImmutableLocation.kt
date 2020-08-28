package me.toonbasic.pascal.location

import java.util.*

data class ImmutableLocation(
        val worldUID: UUID,

        var x: Double,
        var y: Double,
        var z: Double,

        var yaw:   Float,
        var pitch: Float
)