package io.github.obimp.connection

import org.bouncycastle.tls.TlsServer
import org.bouncycastle.tls.TlsServerProtocol
import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

/**
 * @author Alexander Krysin
 */
internal class SecureOBIMPClientConnection(
    private val channel: SocketChannel,
    private val tlsServer: TlsServer
) : AbstractOBIMPClientConnection(channel) {
    private val protocol = TlsServerProtocol()

    fun start() {
        selectionKey.attach(Runnable {
            try {
                if (selectionKey.isReadable) {
                    val buffer = ByteBuffer.allocate(32)
                    while (channel.read(buffer) > 0) {
                        buffer.flip()
                        protocol.offerInput(buffer.array(), 0, buffer.remaining())
                        buffer.clear()
                    }
                } else if (selectionKey.isWritable) {
                    val availableOutputBytes = protocol.availableOutputBytes
                    val buffer = ByteBuffer.allocate(availableOutputBytes)
                    protocol.readOutput(buffer, availableOutputBytes)
                    buffer.flip()
                    channel.write(buffer)
                }
            } catch (e: Exception) {
                println("Client ${channel.remoteAddress} was disconnect")
                protocol.close()
                channel.close()
            }
        })
        protocol.accept(tlsServer)
        TLSProcessor.register(protocol, this)
        TLSProcessor.start()
    }

    fun writeData() {
        while (outputCache.isNotEmpty()) {
            val packet = outputCache.removeFirst()
            val buffer = packet.toBytes()
            protocol.writeApplicationData(buffer.array(), 0, buffer.remaining())
        }
    }
}