package io.github.obimp.connection.input

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.LongWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.handle.OBIMPPacketHandler
import io.github.obimp.packet.header.OBIMPHeader
import java.nio.ByteBuffer

/**
 * @author Alexander Krysin
 */
internal object OBIMPInputDataParser : InputDataParser {
    private val packetHandler = OBIMPPacketHandler()
    private val inputBuffer = mutableMapOf<OBIMPClientConnection, Pair<ByteBuffer, OBIMPHeader?>>()

    override fun parseInputData(connection: OBIMPClientConnection, buffer: ByteBuffer) {
        buffer.rewind()
        inputBuffer[connection]?.let { pair ->
            val (currentBuffer, header) = pair
            header?.let {
                val neededBytesCount = it.contentLength - currentBuffer.capacity()
                if (buffer.capacity() >= neededBytesCount) {
                    val bodyBytes = ByteArray(neededBytesCount)
                    buffer[bodyBytes]
                    parseBody(connection, it, ByteBuffer.wrap(currentBuffer.array() + bodyBytes))
                    inputBuffer.remove(connection)
                    if (buffer.hasRemaining()) {
                        parseInputData(connection, ByteBuffer.allocate(buffer.remaining()).put(buffer))
                    }
                } else {
                    val actualBuffer = ByteBuffer.allocate(currentBuffer.capacity() + buffer.capacity())
                    currentBuffer.rewind()
                    actualBuffer.put(currentBuffer)
                    actualBuffer.put(buffer)
                    inputBuffer.replace(connection, Pair(actualBuffer, it))
                }
                return
            }
            val neededForHeaderBytesCount = 17 - currentBuffer.capacity()
            if (buffer.capacity() >= neededForHeaderBytesCount) {
                val neededForHeaderBytes = ByteArray(neededForHeaderBytesCount)
                buffer[neededForHeaderBytes]
                val headerBytes = ByteBuffer.wrap(currentBuffer.array() + neededForHeaderBytes)
                val actualHeader = parseHeader(headerBytes)
                if (buffer.remaining() >= actualHeader.contentLength) {
                    val bodyBytes = ByteArray(actualHeader.contentLength)
                    buffer[bodyBytes]
                    parseBody(connection, actualHeader, ByteBuffer.wrap(bodyBytes))
                    inputBuffer.remove(connection)
                    if (buffer.hasRemaining()) {
                        parseInputData(connection, ByteBuffer.allocate(buffer.remaining()).put(buffer))
                    }
                } else {
                    inputBuffer.replace(connection, Pair(ByteBuffer.allocate(buffer.remaining()).put(buffer), actualHeader))
                }
            } else {
                inputBuffer.replace(connection, Pair(ByteBuffer.wrap(currentBuffer.array() + buffer.array()), null))
            }
            return
        }
        if (buffer.capacity() >= 17) {
            val headerBytes = ByteArray(17)
            buffer[headerBytes]
            val header = parseHeader(ByteBuffer.wrap(headerBytes))
            if (buffer.remaining() >= header.contentLength) {
                val bodyBytes = ByteArray(header.contentLength)
                buffer[bodyBytes]
                parseBody(connection, header, ByteBuffer.wrap(bodyBytes))
                if (buffer.hasRemaining()) {
                    parseInputData(connection, ByteBuffer.allocate(buffer.remaining()).put(buffer))
                }
            } else {
                val actualBuffer = ByteBuffer.allocate(buffer.remaining()).put(buffer)
                actualBuffer.rewind()
                inputBuffer[connection] = Pair(actualBuffer, header)
            }
        } else {
            inputBuffer[connection] = Pair(ByteBuffer.wrap(buffer.array()), null)
        }
    }

    private fun parseHeader(buffer: ByteBuffer): OBIMPHeader {
        buffer.get() // Skipping check byte (0x23 - "#")
        val sequence = buffer.int
        val type = buffer.short
        val subtype = buffer.short
        val requestID = buffer.int
        val contentLength = buffer.int
        return OBIMPHeader(sequence, type, subtype, requestID, contentLength)
    }

    private fun parseBody(connection: OBIMPClientConnection, header: OBIMPHeader, buffer: ByteBuffer) {
        val packet = OBIMPPacket(header)
        if (header.contentLength > 0) {
            while (buffer.hasRemaining()) {
                val type = buffer.int
                val length = buffer.int
                val wtld = WTLD(LongWord(type))
                if (length > 0) {
                    val data = ByteArray(length)
                    buffer[data]
                    wtld.buffer = ByteBuffer.wrap(data)
                }
                packet.addItem(wtld)
            }
        }
        packetHandler.handlePacket(connection, packet)
    }
}