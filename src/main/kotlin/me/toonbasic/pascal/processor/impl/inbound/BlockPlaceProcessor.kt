package me.toonbasic.pascal.processor.impl.inbound

import me.toonbasic.pascal.processor.type.Processor
import me.toonbasic.pascal.specimen.Specimen
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace
import kotlin.reflect.KClass

class BlockPlaceProcessor : Processor() {

    override val packetTypes: List<KClass<*>> = listOf(PacketPlayInBlockPlace::class)

    override fun process(specimen: Specimen, packet: Packet<*>) {
        specimen.placing = false
    }

}