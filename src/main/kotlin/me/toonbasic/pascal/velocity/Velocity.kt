package me.toonbasic.pascal.velocity

import net.minecraft.server.v1_8_R3.MathHelper
import kotlin.math.hypot


class Velocity(private val x: Double, private val y: Double, private val z: Double) {
    private val horizontal:   Double = hypot(x, z)
    private var ticksExisted: Int = (((x + z) / 2.0 + 2.0) * 15.0).toInt()

    fun onMove() {
        --ticksExisted
    }

    fun hasExpired(): Boolean {
        return ticksExisted < 0
    }

}