package com.labkeep.domain.port.output

import com.labkeep.domain.model.*

// ── Usuario ──────────────────────────────────────────────────────────────────
interface UsuarioRepository {
    fun buscarPorCorreo(correo: String): Usuario?
    fun existeCorreo(correo: String): Boolean
    fun guardar(usuario: Usuario)
    fun actualizarContrasena(correo: String, hashNuevo: String)
}

// ── Categoria ─────────────────────────────────────────────────────────────────
interface CategoriaRepository {
    fun findAll(): List<Categoria>
    fun findById(id: Int): Categoria?
    fun save(categoria: Categoria)
    fun update(categoria: Categoria): Boolean
    fun delete(id: Int): Boolean
}

// ── Dispositivo ───────────────────────────────────────────────────────────────
interface DispositivoRepository {
    fun findAll(): List<Dispositivo>
    fun findById(id: Int): Dispositivo?
    fun findByQuery(query: String): List<Dispositivo>
    fun save(dispositivo: Dispositivo): Int
    fun update(dispositivo: Dispositivo): Boolean
    fun delete(id: Int): Boolean
    fun actualizarEstado(id: Int, estado: EstadoDispositivo): Boolean
    fun actualizarImagen(id: Int, url: String, publicId: String): Boolean
}

// ── Prestamo ──────────────────────────────────────────────────────────────────
interface PrestamoRepository {
    fun findAll(): List<Prestamo>
    fun findByDispositivo(dispositivoId: Int): List<Prestamo>
    fun findPrestamoActivo(dispositivoId: Int): Prestamo?
    fun save(prestamo: Prestamo): Int
    fun devolver(prestamoId: Int): Boolean
}

// ── Cloudinary ────────────────────────────────────────────────────────────────
interface ImagenStorage {
    fun subir(bytes: ByteArray, nombreArchivo: String): ImagenResult
    fun eliminar(publicId: String)
}

data class ImagenResult(val url: String, val publicId: String)
