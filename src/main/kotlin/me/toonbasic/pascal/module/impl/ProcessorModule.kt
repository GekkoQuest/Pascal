package me.toonbasic.pascal.module.impl

import me.toonbasic.pascal.PascalPlugin
import me.toonbasic.pascal.module.type.PluginModule
import me.toonbasic.pascal.processor.impl.inbound.BlockPlaceProcessor
import me.toonbasic.pascal.processor.impl.inbound.DiggingProcessor
import me.toonbasic.pascal.processor.impl.inbound.PlayerProcessor
import me.toonbasic.pascal.processor.impl.inbound.UseEntityProcessor
import me.toonbasic.pascal.processor.type.Processor
import me.toonbasic.pascal.specimen.Specimen
import net.minecraft.server.v1_8_R3.Packet

class ProcessorModule(override val plugin: PascalPlugin) : PluginModule() {

    private val pipeline = mutableListOf<Processor>()

    override fun onEnable() {
        pipeline.add(BlockPlaceProcessor())
        pipeline.add(DiggingProcessor())
        pipeline.add(PlayerProcessor())
        pipeline.add(UseEntityProcessor())
    }

    override fun onDisable() {
        pipeline.clear()
    }

    operator fun invoke(specimen: Specimen, packet: Packet<*>) {
        pipeline.filter { packet::class in it.packetTypes }.forEach { it.process(specimen, packet) }
    }

}