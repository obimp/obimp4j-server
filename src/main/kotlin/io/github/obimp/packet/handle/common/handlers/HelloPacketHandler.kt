package io.github.obimp.packet.handle.common.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.LongWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_COM
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.common.CommonPacketHandler.Companion.OBIMP_BEX_COM_SRV_HELLO
import io.github.obimp.packet.header.OBIMPHeader

/**
 * @author Alexander Krysin
 */
internal class HelloPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(OBIMPHeader(type = OBIMP_BEX_COM, subtype = OBIMP_BEX_COM_SRV_HELLO)).apply {
                addItem(WTLD(LongWord(0x0007)))
            })
    }
}