package io.github.obimp.connection

import org.bouncycastle.tls.TlsServerProtocol
import java.nio.ByteBuffer
import kotlin.concurrent.thread

/**
 * @author Alexander Krysin
 */
internal object TLSProcessor {
    private val protocols = mutableMapOf<TlsServerProtocol, SecureOBIMPClientConnection>()

    fun start() {
        if (protocols.isNotEmpty()) {
            thread {
                while (protocols.isNotEmpty()) {
                    for ((protocol, connection) in protocols) {
                        if (protocol.isConnected) {
                            val availableInputBytes = protocol.availableInputBytes
                            if (availableInputBytes > 0) {
                                val buffer = ByteBuffer.allocate(availableInputBytes)
                                protocol.readInput(buffer, availableInputBytes)
                                buffer.flip()
                                //OBIMPInputDataParser.parseInputData(connection, buffer)
                                println(String(buffer.array()))
                            }
                            connection.writeData()
                        }
                        if (protocol.isClosed) {
                            protocols.remove(protocol)
                        }
                    }
                }
            }
        }
    }

    fun register(protocol: TlsServerProtocol, connection: SecureOBIMPClientConnection) {
        protocols[protocol] = connection
    }
}