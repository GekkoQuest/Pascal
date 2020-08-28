package me.toonbasic.pascal.module.impl

import kotlinx.coroutines.asCoroutineDispatcher
import me.toonbasic.pascal.PascalPlugin
import me.toonbasic.pascal.module.type.PluginModule
import me.toonbasic.pascal.packet.PacketHandler
import me.toonbasic.pascal.specimen.Specimen
import net.minecraft.server.v1_8_R3.EntityPlayer
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import java.util.concurrent.Executors
import kotlin.math.max

class SpecimenModule(override val plugin: PascalPlugin) : PluginModule() {

    private val data = mutableMapOf<UUID, Specimen>()

    private val threadPoolDispatcher = Executors.newFixedThreadPool(max(Runtime.getRuntime().availableProcessors(), 64)).asCoroutineDispatcher()

    override fun onEnable() {
        plugin.server.onlinePlayers.forEach {
            val specimen = Specimen(it)
            data[it.uniqueId] = specimen

            injectPlayer(it, specimen)
        }
    }

    override fun onDisable() {
        plugin.server.onlinePlayers.forEach { ejectPlayer(it) }

        data.clear()
    }

    fun data(player: Player) : Specimen? {
        return data[player.uniqueId]
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val specimen = Specimen(event.player)
        data[event.player.uniqueId] = specimen

        injectPlayer(event.player, specimen)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        ejectPlayer(event.player)

        data.remove(event.player.uniqueId)
    }

    private fun injectPlayer(player: Player, specimen: Specimen) {
        val entityPlayer: EntityPlayer = (player as CraftPlayer).handle

        val pipeline = entityPlayer.playerConnection.networkManager.channel.pipeline()

        threadPoolDispatcher.executor.execute { pipeline.addBefore("packet_handler", "pascal_packet_handler", PacketHandler(plugin, specimen)) }
    }

    private fun ejectPlayer(player: Player) {
        val entityPlayer: EntityPlayer = (player as CraftPlayer).handle

        val channel = entityPlayer.playerConnection.networkManager.channel

        threadPoolDispatcher.executor.execute { channel.eventLoop().execute { channel.pipeline().remove("pascal_packet_handler") } }
    }
}