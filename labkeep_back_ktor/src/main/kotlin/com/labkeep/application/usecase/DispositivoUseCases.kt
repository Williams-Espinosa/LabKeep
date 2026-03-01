package com.labkeep.application.usecase

import com.labkeep.domain.model.*
import com.labkeep.domain.port.input.*
import com.labkeep.domain.port.output.*

class ListarDispositivosUseCaseImpl(private val repo: DispositivoRepository) : ListarDispositivosUseCase {
    override fun ejecutar(query: String?) =
        if (!query.isNullOrBlank()) repo.findByQuery(query) else repo.findAll()
}

class ObtenerDispositivoUseCaseImpl(private val repo: DispositivoRepository) : ObtenerDispositivoUseCase {
    override fun ejecutar(id: Int) =
        repo.findById(id) ?: throw NotFoundException("Dispositivo", id)
}

class CrearDispositivoUseCaseImpl(
    private val repo: DispositivoRepository,
    private val storage: ImagenStorage
) : CrearDispositivoUseCase {
    override fun ejecutar(nombre: String, categoriaId: Int, imagen: ByteArray?, nombreArchivo: String?): Dispositivo {
        if (nombre.isBlank()) throw ValidationException("El nombre es obligatorio")
        var imagenUrl: String? = null
        var imagenPublicId: String? = null
        if (imagen != null && nombreArchivo != null) {
            val result = storage.subir(imagen, nombreArchivo)
            imagenUrl = result.url
            imagenPublicId = result.publicId
        }
        val dispositivo = Dispositivo(
            nombre = nombre,
            categoriaId = categoriaId,
            estado = EstadoDispositivo.DISPONIBLE,
            imagenUrl = imagenUrl,
            imagenPublicId = imagenPublicId
        )
        val id = repo.save(dispositivo)
        return dispositivo.copy(id = id)
    }
}

class ActualizarDispositivoUseCaseImpl(private val repo: DispositivoRepository) : ActualizarDispositivoUseCase {
    override fun ejecutar(id: Int, nombre: String, categoriaId: Int, estado: String): Boolean {
        repo.findById(id) ?: throw NotFoundException("Dispositivo", id)
        return repo.update(Dispositivo(id = id, nombre = nombre, categoriaId = categoriaId, estado = EstadoDispositivo.from(estado)))
    }
}

class ActualizarImagenUseCaseImpl(
    private val repo: DispositivoRepository,
    private val storage: ImagenStorage
) : ActualizarImagenUseCase {
    override fun ejecutar(id: Int, imagen: ByteArray, nombreArchivo: String): String {
        val dispositivo = repo.findById(id) ?: throw NotFoundException("Dispositivo", id)
        dispositivo.imagenPublicId?.let { storage.eliminar(it) }
        val result = storage.subir(imagen, nombreArchivo)
        repo.actualizarImagen(id, result.url, result.publicId)
        return result.url
    }
}

class CambiarEstadoUseCaseImpl(private val repo: DispositivoRepository) : CambiarEstadoUseCase {
    override fun ejecutar(id: Int, estado: String): Boolean {
        repo.findById(id) ?: throw NotFoundException("Dispositivo", id)
        return repo.actualizarEstado(id, EstadoDispositivo.from(estado))
    }
}

class EliminarDispositivoUseCaseImpl(
    private val repo: DispositivoRepository,
    private val storage: ImagenStorage
) : EliminarDispositivoUseCase {
    override fun ejecutar(id: Int): Boolean {
        val dispositivo = repo.findById(id) ?: throw NotFoundException("Dispositivo", id)
        dispositivo.imagenPublicId?.let { storage.eliminar(it) }
        return repo.delete(id)
    }
}
