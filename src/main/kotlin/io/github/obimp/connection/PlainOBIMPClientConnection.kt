package io.github.obimp.connection

import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

/**
 * @author Alexander Krysin
 */
internal class PlainOBIMPClientConnection(channel: SocketChannel) : AbstractOBIMPClientConnection(channel) {
    init {
        selectionKey.attach(Runnable {
            try {
                if (selectionKey.isReadable) {
                    val buffer = ByteBuffer.allocate(32)
                    while (channel.read(buffer) > 0) {
                        buffer.flip()
                        //OBIMPInputDataParser.parseInputData(connection, buffer)
                        println(String(buffer.array()))
                        buffer.clear()
                    }
                } else if (selectionKey.isWritable) {
                    while (outputCache.isNotEmpty()) {
                        channel.write(outputCache.removeFirst().toBytes())
                    }
                }
            } catch (e: Exception) {
                println("Client ${channel.remoteAddress} was disconnect")
                channel.close()
            }
        })
    }
}