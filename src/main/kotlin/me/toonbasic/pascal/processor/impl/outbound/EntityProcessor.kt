package me.toonbasic.pascal.processor.impl.outbound

import me.toonbasic.pascal.location.ImmutableLocation
import me.toonbasic.pascal.processor.type.Processor
import me.toonbasic.pascal.specimen.Specimen
import me.toonbasic.pascal.specimen.storage.EntityStorage
import me.toonbasic.pascal.util.ReflectionUtil
import net.minecraft.server.v1_8_R3.EntityPlayer
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity
import org.bukkit.entity.Player
import kotlin.reflect.KClass

class EntityProcessor : Processor() {

    override val packetTypes: List<KClass<*>> = listOf(PacketPlayOutEntity::class)

    override fun process(specimen: Specimen, packet: Packet<*>) {
        val outEntity = packet as PacketPlayOutEntity

        val entityId = ReflectionUtil.getFieldValue<Int>(outEntity::class.java, "a", Int::class.java, packet)
        val entity = specimen.entityPlayer.world.a(entityId)

        if (entity is EntityPlayer) {
            val targetPlayer = entity.bukkitEntity as Player

            val x = ReflectionUtil.getFieldValue<Byte>(outEntity::class.java, "b", Byte::class.java, packet) / 32.0
            val y = ReflectionUtil.getFieldValue<Byte>(outEntity::class.java, "c", Byte::class.java, packet) / 32.0
            val z = ReflectionUtil.getFieldValue<Byte>(outEntity::class.java, "d", Byte::class.java, packet) / 32.0

            val yaw   = ReflectionUtil.getFieldValue<Byte>(outEntity::class.java, "e", Byte::class.java, packet) * 360.0f / 256.0f
            val pitch = ReflectionUtil.getFieldValue<Byte>(outEntity::class.java, "f", Byte::class.java, packet) * 360.0f / 256.0f

            val location = ImmutableLocation(targetPlayer.world.uid, x, y, z, yaw, pitch)

            if (specimen.entityStorage.containsKey(targetPlayer.uniqueId)) {
                if (specimen.entityStorage[targetPlayer.uniqueId]!!.locationHistory.size > 20) {
                    specimen.entityStorage[targetPlayer.uniqueId]!!.locationHistory.remove(0)
                }

                specimen.entityStorage[targetPlayer.uniqueId]!!.locationHistory[System.currentTimeMillis()] = location
            } else {
                specimen.entityStorage[targetPlayer.uniqueId] = EntityStorage()
                specimen.entityStorage[targetPlayer.uniqueId]!!.locationHistory[System.currentTimeMillis()] = location
            }
        }
    }

}