package me.toonbasic.pascal.packet

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import me.toonbasic.pascal.PascalPlugin
import me.toonbasic.pascal.location.ImmutableLocation
import me.toonbasic.pascal.specimen.Specimen
import net.minecraft.server.v1_8_R3.*

class PacketHandler(
        private val plugin: PascalPlugin,
        private val specimen: Specimen
) : ChannelDuplexHandler() {

    override fun channelRead(channelHandlerContext: ChannelHandlerContext, msg: Any?) {
        super.channelRead(channelHandlerContext, msg)

        val packet = msg as Packet<PacketListenerPlayIn>

        plugin.processorModule.invoke(specimen, packet)
    }

    override fun write(channelHandlerContext: ChannelHandlerContext, msg: Any?, channelPromise: ChannelPromise) {
        super.write(channelHandlerContext, msg, channelPromise)

        val packet = msg as Packet<PacketListenerPlayOut>

        plugin.processorModule.invoke(specimen, packet)
    }
}