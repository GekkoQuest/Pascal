package me.toonbasic.pascal.module.type

import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

abstract class PluginModule : RequiresPlugin, Listener, Module {

    final override var isEnabled = false
        private set

    protected open fun onEnable()  = Unit
    protected open fun onDisable() = Unit


    override fun enable() {
        if (!isEnabled) {
            onEnable()

            plugin.server.pluginManager.registerEvents(this, plugin)

            isEnabled = true;
        }
    }

    override fun disable() {
        if (isEnabled) {
            onDisable()

            HandlerList.unregisterAll(this)

            isEnabled = false;
        }
    }

}