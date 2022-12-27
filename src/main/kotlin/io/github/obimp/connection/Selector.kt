package io.github.obimp.connection

import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.spi.AbstractSelectableChannel
import kotlin.concurrent.thread

/**
 * @author Alexander Krysin
 */
internal object Selector {
    private var selector = Selector.open()

    fun start() {
        if (!selector.isOpen) {
            selector = Selector.open()
        }
        thread {
            while (selector.isOpen) {
                selector.select {
                    (it.attachment() as Runnable).run()
                }
            }
        }
    }

    fun register(channel: AbstractSelectableChannel, ops: Int): SelectionKey {
        return channel.register(selector, ops)
    }

    fun stop() {
        if (selector.keys().isEmpty()) {
            selector.close()
        }
    }
}