package io.github.obimp

import io.github.obimp.connection.SecureOBIMPServerConnection

fun main() {
    val certPath = "C:/BimoidServer/BimoidSrv/ObimpSsl/Certif/DESKTOP-HODFC4O.pem"
    val caCertPath = "C:/BimoidServer/BimoidSrv/ObimpSsl/Certif/OBIMP_ca_124578.pem"
    val privateKeyPath = "C:/BimoidServer/BimoidSrv/ObimpSsl/Certif/DESKTOP-HODFC4O.key.pem"
    val connection = SecureOBIMPServerConnection(8888, certPath, caCertPath, privateKeyPath)
    connection.connect()
}