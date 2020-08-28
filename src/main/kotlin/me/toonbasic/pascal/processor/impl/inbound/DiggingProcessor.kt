package me.toonbasic.pascal.processor.impl.inbound

import me.toonbasic.pascal.processor.type.Processor
import me.toonbasic.pascal.specimen.Specimen
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig
import kotlin.reflect.KClass

class DiggingProcessor : Processor() {

    override val packetTypes: List<KClass<*>> = listOf(PacketPlayInBlockDig::class)

    override fun process(specimen: Specimen, packet: Packet<*>) {
        val digging = packet as PacketPlayInBlockDig

        when (digging.c()) {
            PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK -> specimen.digging = true
            PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK -> specimen.digging = false
            PacketPlayInBlockDig.EnumPlayerDigType.STOP_DESTROY_BLOCK ->  specimen.digging = false
            else -> { }
        }
    }

}