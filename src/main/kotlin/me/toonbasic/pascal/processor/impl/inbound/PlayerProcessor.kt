package me.toonbasic.pascal.processor.impl.inbound

import me.toonbasic.pascal.location.ImmutableLocation
import me.toonbasic.pascal.processor.type.Processor
import me.toonbasic.pascal.specimen.Specimen
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketPlayInFlying
import kotlin.reflect.KClass

class PlayerProcessor : Processor() {

    override val packetTypes: List<KClass<*>> = listOf(
            PacketPlayInFlying::class,
            PacketPlayInFlying.PacketPlayInPosition::class,
            PacketPlayInFlying.PacketPlayInPositionLook::class,
            PacketPlayInFlying.PacketPlayInLook::class
    )

    override fun process(specimen: Specimen, packet: Packet<*>) {
        val flying = packet as PacketPlayInFlying

        val x = flying.a()
        val y = flying.b()
        val z = flying.c()

        val yaw = flying.d()
        val pitch = flying.e()

        val hasPos  = flying.g()
        val hasLook = flying.h()

        val newLocation = ImmutableLocation(specimen.player.uniqueId, x, y, z, yaw, pitch)
        val oldLocation = specimen.toLocation

        if (!hasPos) {
            newLocation.x = oldLocation.x
            newLocation.y = oldLocation.y
            newLocation.z = oldLocation.z
        }

        if (!hasLook) {
            newLocation.yaw = oldLocation.yaw
            newLocation.pitch = oldLocation.pitch
        }

        specimen.fromLocation = oldLocation
        specimen.toLocation = newLocation
    }

}