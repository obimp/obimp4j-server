package io.github.obimp.packet.handle.common.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.LongWord
import io.github.obimp.data.type.VersionQuadWord
import io.github.obimp.data.type.Word
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_CL
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_COM
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_FT
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_IM
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_PRES
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_TP
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_UA
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_UD
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.cl.ContactListPacketHandler.Companion.OBIMP_BEX_CL_SRV_END_UPDATE
import io.github.obimp.packet.handle.common.CommonPacketHandler.Companion.OBIMP_BEX_COM_SRV_LOGIN_REPLY
import io.github.obimp.packet.handle.ft.FileTransferPacketHandler.Companion.OBIMP_BEX_FT_CLI_SRV_CONTROL
import io.github.obimp.packet.handle.im.InstantMessagingPacketHandler.Companion.OBIMP_BEX_IM_CLI_MULTIPLE_MSG
import io.github.obimp.packet.handle.presence.PresencePacketHandler.Companion.OBIMP_BEX_PRES_SRV_OWN_MAIL_URL
import io.github.obimp.packet.handle.transports.TransportsPacketHandler.Companion.OBIMP_BEX_TP_SRV_OWN_AVATAR_HASH
import io.github.obimp.packet.handle.ua.UserAvatarsPacketHandler.Companion.OBIMP_BEX_UA_SRV_AVATAR_SET_REPLY
import io.github.obimp.packet.handle.ud.UsersDirectoryPacketHandler.Companion.OBIMP_BEX_UD_SRV_SECURE_UPD_REPLY
import io.github.obimp.packet.header.OBIMPHeader
import io.github.obimp.util.Version

/**
 * @author Alexander Krysin
 */
internal class LoginPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_COM,
                    subtype = OBIMP_BEX_COM_SRV_LOGIN_REPLY
                )
            ).apply {
                addItem(
                    WTLD(
                        LongWord(0x0002),
                        Word(OBIMP_BEX_CL),
                        Word(OBIMP_BEX_CL_SRV_END_UPDATE),
                        Word(OBIMP_BEX_PRES),
                        Word(OBIMP_BEX_PRES_SRV_OWN_MAIL_URL),
                        Word(OBIMP_BEX_IM),
                        Word(OBIMP_BEX_IM_CLI_MULTIPLE_MSG),
                        Word(OBIMP_BEX_UD),
                        Word(OBIMP_BEX_UD_SRV_SECURE_UPD_REPLY),
                        Word(OBIMP_BEX_UA),
                        Word(OBIMP_BEX_UA_SRV_AVATAR_SET_REPLY),
                        Word(OBIMP_BEX_FT),
                        Word(OBIMP_BEX_FT_CLI_SRV_CONTROL),
                        Word(OBIMP_BEX_TP),
                        Word(OBIMP_BEX_TP_SRV_OWN_AVATAR_HASH)
                    )
                )
                addItem(WTLD(LongWord(0x0003), LongWord(131072)))
                addItem(
                    WTLD(
                        LongWord(0x0008),
                        VersionQuadWord(Version(OBIMP_BEX_CL.toInt(), 2, 1, 1)),
                        VersionQuadWord(Version(OBIMP_BEX_PRES.toInt(), 2, 1, 1)),
                        VersionQuadWord(Version(OBIMP_BEX_IM.toInt(), 2, 1, 1)),
                        VersionQuadWord(Version(OBIMP_BEX_UD.toInt(), 2, 1, 1)),
                        VersionQuadWord(Version(OBIMP_BEX_UA.toInt(), 2, 1, 1)),
                        VersionQuadWord(Version(OBIMP_BEX_FT.toInt(), 2, 1, 1)),
                        VersionQuadWord(Version(OBIMP_BEX_TP.toInt(), 2, 1, 1))
                    )
                )
            })
    }
}