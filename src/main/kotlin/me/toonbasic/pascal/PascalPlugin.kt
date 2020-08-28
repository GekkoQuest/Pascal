package me.toonbasic.pascal

import me.toonbasic.pascal.module.impl.ProcessorModule
import me.toonbasic.pascal.module.impl.SpecimenModule
import org.bukkit.plugin.java.JavaPlugin

class PascalPlugin : JavaPlugin() {

    val specimenModule  = SpecimenModule(this)
    val processorModule = ProcessorModule(this)

    override fun onEnable() {
        specimenModule .enable()
        processorModule.enable()
    }

    override fun onDisable() {
        specimenModule .disable()
        processorModule.disable()
    }

}