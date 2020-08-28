package me.toonbasic.pascal.processor.type

import me.toonbasic.pascal.specimen.Specimen
import net.minecraft.server.v1_8_R3.Packet
import kotlin.reflect.KClass

abstract class Processor() {
    internal abstract val packetTypes: List<KClass<*>>

    abstract fun process(specimen: Specimen, packet: Packet<*>)
}