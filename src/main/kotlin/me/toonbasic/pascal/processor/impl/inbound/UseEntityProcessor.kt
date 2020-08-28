package me.toonbasic.pascal.processor.impl.inbound

import me.toonbasic.pascal.processor.type.Processor
import me.toonbasic.pascal.specimen.Specimen
import net.minecraft.server.v1_8_R3.*
import kotlin.reflect.KClass

class UseEntityProcessor : Processor() {

    override val packetTypes: List<KClass<*>> = listOf(PacketPlayInUseEntity::class)

    override fun process(specimen: Specimen, packet: Packet<*>) {
        val useEntity = packet as PacketPlayInUseEntity

        val world = specimen.entityPlayer.world
        val entity = packet.a(world)

        if (entity is EntityPlayer) {
            specimen.attacking = true
        }
    }

}