package com.labkeep.application.usecase

import com.labkeep.domain.model.*
import com.labkeep.domain.port.input.*
import com.labkeep.domain.port.output.*

class ListarPrestamosUseCaseImpl(private val repo: PrestamoRepository) : ListarPrestamosUseCase {
    override fun ejecutar() = repo.findAll()
}

class HistorialDispositivoUseCaseImpl(private val repo: PrestamoRepository) : HistorialDispositivoUseCase {
    override fun ejecutar(dispositivoId: Int) = repo.findByDispositivo(dispositivoId)
}

class PrestarDispositivoUseCaseImpl(
    private val prestamoRepo: PrestamoRepository,
    private val dispositivoRepo: DispositivoRepository
) : PrestarDispositivoUseCase {
    override fun ejecutar(dispositivoId: Int, usuarioId: Int): Prestamo {
        val dispositivo = dispositivoRepo.findById(dispositivoId)
            ?: throw NotFoundException("Dispositivo", dispositivoId)

        if (dispositivo.estado != EstadoDispositivo.DISPONIBLE)
            throw ConflictException("El dispositivo '${dispositivo.nombre}' no está disponible")

        val prestamo = Prestamo(dispositivoId = dispositivoId, usuarioId = usuarioId)
        val id = prestamoRepo.save(prestamo)
        dispositivoRepo.actualizarEstado(dispositivoId, EstadoDispositivo.PRESTADO)
        return prestamo.copy(id = id)
    }
}

class DevolverDispositivoUseCaseImpl(
    private val prestamoRepo: PrestamoRepository,
    private val dispositivoRepo: DispositivoRepository
) : DevolverDispositivoUseCase {
    override fun ejecutar(dispositivoId: Int): Boolean {
        val prestamo = prestamoRepo.findPrestamoActivo(dispositivoId)
            ?: throw ConflictException("No hay préstamo activo para este dispositivo")
        val ok = prestamoRepo.devolver(prestamo.id)
        if (ok) dispositivoRepo.actualizarEstado(dispositivoId, EstadoDispositivo.DISPONIBLE)
        return ok
    }
}
