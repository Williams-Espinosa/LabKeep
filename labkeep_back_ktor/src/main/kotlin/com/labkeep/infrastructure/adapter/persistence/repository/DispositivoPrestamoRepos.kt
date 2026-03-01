package com.labkeep.infrastructure.adapter.persistence.repository

import com.labkeep.domain.model.*
import com.labkeep.domain.port.output.DispositivoRepository
import com.labkeep.domain.port.output.PrestamoRepository
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime as JLocalDateTime

class DispositivoRepositoryImpl : DispositivoRepository {

    private val baseQuery = DispositivoTable
        .join(CategoriaTable, JoinType.INNER, DispositivoTable.categoriaId, CategoriaTable.id)

    override fun findAll(): List<Dispositivo> = transaction {
        baseQuery.selectAll()
            .orderBy(DispositivoTable.fechaCreacion, SortOrder.DESC)
            .map { mapear(it) }
    }

    override fun findById(id: Int): Dispositivo? = transaction {
        baseQuery.selectAll()
            .where { DispositivoTable.id eq id }
            .map { mapear(it) }
            .firstOrNull()
    }

    override fun findByQuery(query: String): List<Dispositivo> = transaction {
        val param = "%${query.lowercase()}%"
        baseQuery.selectAll()
            .where {
                (DispositivoTable.nombre.lowerCase() like param) or
                        (CategoriaTable.nombre.lowerCase() like param)
            }
            .orderBy(DispositivoTable.fechaCreacion, SortOrder.DESC)
            .map { mapear(it) }
    }

    override fun save(dispositivo: Dispositivo): Int = transaction {
        DispositivoTable.insertAndGetId {
            it[nombre]         = dispositivo.nombre
            it[categoriaId]    = dispositivo.categoriaId
            it[estado]         = dispositivo.estado.valor
            it[imagenUrl]      = dispositivo.imagenUrl
            it[imagenPublicId] = dispositivo.imagenPublicId
        }.value
    }

    override fun update(dispositivo: Dispositivo): Boolean = transaction {
        DispositivoTable.update({ DispositivoTable.id eq dispositivo.id }) {
            it[nombre]      = dispositivo.nombre
            it[categoriaId] = dispositivo.categoriaId
            it[estado]      = dispositivo.estado.valor
        } > 0
    }

    override fun delete(id: Int): Boolean = transaction {
        DispositivoTable.deleteWhere { DispositivoTable.id eq id } > 0
    }

    override fun actualizarEstado(id: Int, estado: EstadoDispositivo): Boolean = transaction {
        DispositivoTable.update({ DispositivoTable.id eq id }) {
            it[DispositivoTable.estado] = estado.valor
        } > 0
    }

    override fun actualizarImagen(id: Int, url: String, publicId: String): Boolean = transaction {
        DispositivoTable.update({ DispositivoTable.id eq id }) {
            it[imagenUrl]      = url
            it[imagenPublicId] = publicId
        } > 0
    }

    private fun mapear(row: ResultRow) = Dispositivo(
        id              = row[DispositivoTable.id].value,
        nombre          = row[DispositivoTable.nombre],
        categoriaId     = row[DispositivoTable.categoriaId].value,
        categoriaNombre = row[CategoriaTable.nombre],
        estado          = EstadoDispositivo.from(row[DispositivoTable.estado]),
        imagenUrl       = row[DispositivoTable.imagenUrl],
        imagenPublicId  = row[DispositivoTable.imagenPublicId],
        fechaCreacion   = row[DispositivoTable.fechaCreacion].toKotlinLocalDateTime()
    )
}

class PrestamoRepositoryImpl : PrestamoRepository {

    private val baseQuery = PrestamoTable
        .join(DispositivoTable, JoinType.INNER, PrestamoTable.dispositivoId, DispositivoTable.id)
        .join(UsuarioTable,     JoinType.INNER, PrestamoTable.usuarioId,     UsuarioTable.id)

    override fun findAll(): List<Prestamo> = transaction {
        baseQuery.selectAll()
            .orderBy(PrestamoTable.fechaPrestamo, SortOrder.DESC)
            .map { mapear(it) }
    }

    override fun findByDispositivo(dispositivoId: Int): List<Prestamo> = transaction {
        baseQuery.selectAll()
            .where { PrestamoTable.dispositivoId eq dispositivoId }
            .orderBy(PrestamoTable.fechaPrestamo, SortOrder.DESC)
            .map { mapear(it) }
    }

    override fun findPrestamoActivo(dispositivoId: Int): Prestamo? = transaction {
        baseQuery.selectAll()
            .where {
                (PrestamoTable.dispositivoId eq dispositivoId) and
                        PrestamoTable.fechaDevolucion.isNull()
            }
            .map { mapear(it) }
            .firstOrNull()
    }

    override fun save(prestamo: Prestamo): Int = transaction {
        PrestamoTable.insertAndGetId {
            it[dispositivoId] = prestamo.dispositivoId
            it[usuarioId]     = prestamo.usuarioId
        }.value
    }

    override fun devolver(prestamoId: Int): Boolean = transaction {
        PrestamoTable.update({
            (PrestamoTable.id eq prestamoId) and PrestamoTable.fechaDevolucion.isNull()
        }) {
            it[fechaDevolucion] = JLocalDateTime.now()
        } > 0
    }

    private fun mapear(row: ResultRow) = Prestamo(
        id                = row[PrestamoTable.id].value,
        dispositivoId     = row[PrestamoTable.dispositivoId].value,
        usuarioId         = row[PrestamoTable.usuarioId].value,
        dispositivoNombre = row[DispositivoTable.nombre],
        usuarioCorreo     = row[UsuarioTable.correo],
        fechaPrestamo     = row[PrestamoTable.fechaPrestamo].toKotlinLocalDateTime(),
        fechaDevolucion   = row[PrestamoTable.fechaDevolucion]?.toKotlinLocalDateTime()
    )
}