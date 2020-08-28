package me.toonbasic.pascal.specimen

import me.toonbasic.pascal.location.ImmutableLocation
import me.toonbasic.pascal.specimen.storage.EntityStorage
import me.toonbasic.pascal.velocity.Velocity
import net.minecraft.server.v1_8_R3.EntityPlayer
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*


class Specimen(val player: Player) {

    // NMS Player
    val entityPlayer: EntityPlayer = (player as CraftPlayer).handle

    // Target Entity
    val entityStorage = mutableMapOf<UUID, EntityStorage>()

    // Locations
    var toLocation   = ImmutableLocation(player.world.uid, 0.0, 0.0, 0.0, 0.0f, 0.0f)
    var fromLocation = ImmutableLocation(player.world.uid, 0.0, 0.0, 0.0, 0.0f, 0.0f)

    // Velocity
    val velocities = mutableListOf<Velocity>()

    // Actions
    var attacking = false
    var digging   = false
    var placing   = false
}