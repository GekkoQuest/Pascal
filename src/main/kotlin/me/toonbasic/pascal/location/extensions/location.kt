package me.toonbasic.pascal.location.extensions

import me.toonbasic.pascal.location.ImmutableLocation
import org.bukkit.Location

fun Location.toImmutable(): ImmutableLocation {
    return ImmutableLocation(world.uid, x, y, z, yaw, pitch)
}