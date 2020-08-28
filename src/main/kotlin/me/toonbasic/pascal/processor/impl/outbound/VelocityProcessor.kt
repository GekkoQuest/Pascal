package me.toonbasic.pascal.processor.impl.outbound

import me.toonbasic.pascal.processor.type.Processor
import me.toonbasic.pascal.specimen.Specimen
import me.toonbasic.pascal.util.ReflectionUtil
import me.toonbasic.pascal.velocity.Velocity
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity
import kotlin.reflect.KClass

class VelocityProcessor : Processor() {

    override val packetTypes: List<KClass<*>> = listOf(PacketPlayOutEntityVelocity::class)

    override fun process(specimen: Specimen, packet: Packet<*>) {
        val velocity = packet as PacketPlayOutEntityVelocity

        val entityId = ReflectionUtil.getFieldValue<Int>(velocity::class.java, "a", Int::class.java, packet)

        if (entityId == specimen.player.entityId) {
            val velocityX = ReflectionUtil.getFieldValue<Int>(velocity::class.java, "b", Int::class.java, packet) / 8000.0
            val velocityY = ReflectionUtil.getFieldValue<Int>(velocity::class.java, "c", Int::class.java, packet) / 8000.0
            val velocityZ = ReflectionUtil.getFieldValue<Int>(velocity::class.java, "d", Int::class.java, packet) / 8000.0

            specimen.velocities.add(Velocity(velocityX, velocityY, velocityZ))
        }
    }

}