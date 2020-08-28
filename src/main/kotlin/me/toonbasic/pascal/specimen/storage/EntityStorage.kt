package me.toonbasic.pascal.specimen.storage

import me.toonbasic.pascal.location.ImmutableLocation

class EntityStorage {
    val locationHistory = mutableMapOf<Long, ImmutableLocation>()
}