package com.labkeep.infrastructure.adapter.persistence.repository

import com.labkeep.domain.model.Categoria
import com.labkeep.domain.model.Usuario
import com.labkeep.domain.port.output.CategoriaRepository
import com.labkeep.domain.port.output.UsuarioRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


class UsuarioRepositoryImpl : UsuarioRepository {

    override fun buscarPorCorreo(correo: String): Usuario? = transaction {
        UsuarioTable.selectAll()
            .where { UsuarioTable.correo eq correo }
            .map { mapearUsuario(it) }
            .firstOrNull()
    }

    override fun existeCorreo(correo: String): Boolean = transaction {
        UsuarioTable.selectAll()
            .where { UsuarioTable.correo eq correo }
            .count() > 0
    }

    override fun guardar(usuario: Usuario): Unit = transaction {
        UsuarioTable.insert {
            it[correo]     = usuario.correo
            it[contrasena] = usuario.contrasena
        }
    }

    override fun actualizarContrasena(correo: String, hashNuevo: String): Unit = transaction {
        UsuarioTable.update({ UsuarioTable.correo eq correo }) {
            it[contrasena] = hashNuevo
        }
    }

    private fun mapearUsuario(row: ResultRow) = Usuario(
        id         = row[UsuarioTable.id].value,
        correo     = row[UsuarioTable.correo],
        contrasena = row[UsuarioTable.contrasena]
    )
}

class CategoriaRepositoryImpl : CategoriaRepository {

    override fun findAll(): List<Categoria> = transaction {
        CategoriaTable.selectAll()
            .orderBy(CategoriaTable.nombre)
            .map { mapearCategoria(it) }
    }

    override fun findById(id: Int): Categoria? = transaction {
        CategoriaTable.selectAll()
            .where { CategoriaTable.id eq id }
            .map { mapearCategoria(it) }
            .firstOrNull()
    }

    override fun save(categoria: Categoria): Unit = transaction {
        CategoriaTable.insert { it[nombre] = categoria.nombre }
    }

    override fun update(categoria: Categoria): Boolean = transaction {
        CategoriaTable.update({ CategoriaTable.id eq categoria.id }) {
            it[nombre] = categoria.nombre
        } > 0
    }

    override fun delete(id: Int): Boolean = transaction {
        CategoriaTable.deleteWhere { CategoriaTable.id eq id } > 0
    }

    private fun mapearCategoria(row: ResultRow) = Categoria(
        id     = row[CategoriaTable.id].value,
        nombre = row[CategoriaTable.nombre]
    )
}
