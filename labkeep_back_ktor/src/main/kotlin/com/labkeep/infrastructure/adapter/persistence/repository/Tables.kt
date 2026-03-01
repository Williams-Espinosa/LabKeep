package com.labkeep.infrastructure.adapter.persistence.repository

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object UsuarioTable : IntIdTable("usuario") {
    val correo     = varchar("correo", 255).uniqueIndex()
    val contrasena = varchar("contrasena", 255)
}

object CategoriaTable : IntIdTable("categoria") {
    val nombre = varchar("nombre", 100)
}

object DispositivoTable : IntIdTable("dispositivo") {
    val nombre         = varchar("nombre", 200)
    val categoriaId    = reference("categoria_id", CategoriaTable)
    val estado         = varchar("estado", 50).default("disponible")
    val imagenUrl      = varchar("imagen_url", 500).nullable()
    val imagenPublicId = varchar("imagen_public_id", 200).nullable()
    val fechaCreacion  = datetime("fecha_creacion").defaultExpression(CurrentDateTime)
}

object PrestamoTable : IntIdTable("prestamo") {
    val dispositivoId   = reference("dispositivo_id", DispositivoTable)
    val usuarioId       = reference("usuario_id", UsuarioTable)
    val fechaPrestamo   = datetime("fecha_prestamo").defaultExpression(CurrentDateTime)
    val fechaDevolucion = datetime("fecha_devolucion").nullable()
}