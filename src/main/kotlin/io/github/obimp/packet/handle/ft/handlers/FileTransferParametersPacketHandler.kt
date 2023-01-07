package io.github.obimp.packet.handle.ft.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.Bool
import io.github.obimp.data.type.LongWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_FT
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.ft.FileTransferPacketHandler.Companion.OBIMP_BEX_FT_SRV_PARAMS_REPLY
import io.github.obimp.packet.header.OBIMPHeader

/**
 * @author Alexander Krysin
 */
internal class FileTransferParametersPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_FT,
                    subtype = OBIMP_BEX_FT_SRV_PARAMS_REPLY
                )
            ).apply {
                addItem(WTLD(LongWord(0x0001), LongWord(255)))
                addItem(WTLD(LongWord(0x0002), LongWord(1024)))
                addItem(WTLD(LongWord(0x0003), LongWord(255)))
                addItem(WTLD(LongWord(0x0004), LongWord(32767)))
                addItem(WTLD(LongWord(0x0005), Bool(true)))
                addItem(WTLD(LongWord(0x0006), Bool(false)))
            })
    }
}