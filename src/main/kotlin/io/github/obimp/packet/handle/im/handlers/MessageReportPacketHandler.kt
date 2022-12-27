package io.github.obimp.packet.handle.im.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.structure.readDataType
import io.github.obimp.data.type.LongWord
import io.github.obimp.data.type.UTF8
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler

/**
 * @author Alexander Krysin
 */
internal class MessageReportPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        val accountName = packet.nextItem().readDataType<UTF8>().value
        val messageId = packet.nextItem().readDataType<LongWord>().value
    }
}