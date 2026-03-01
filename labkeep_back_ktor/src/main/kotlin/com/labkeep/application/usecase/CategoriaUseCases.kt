package com.labkeep.application.usecase

import com.labkeep.domain.model.*
import com.labkeep.domain.port.input.*
import com.labkeep.domain.port.output.CategoriaRepository

class ListarCategoriasUseCaseImpl(private val repo: CategoriaRepository) : ListarCategoriasUseCase {
    override fun ejecutar() = repo.findAll()
}

class ObtenerCategoriaUseCaseImpl(private val repo: CategoriaRepository) : ObtenerCategoriaUseCase {
    override fun ejecutar(id: Int) =
        repo.findById(id) ?: throw NotFoundException("Categoria", id)
}

class CrearCategoriaUseCaseImpl(private val repo: CategoriaRepository) : CrearCategoriaUseCase {
    override fun ejecutar(nombre: String) {
        if (nombre.isBlank()) throw ValidationException("El nombre es obligatorio")
        repo.save(Categoria(nombre = nombre))
    }
}

class ActualizarCategoriaUseCaseImpl(private val repo: CategoriaRepository) : ActualizarCategoriaUseCase {
    override fun ejecutar(id: Int, nombre: String): Boolean {
        if (nombre.isBlank()) throw ValidationException("El nombre es obligatorio")
        repo.findById(id) ?: throw NotFoundException("Categoria", id)
        return repo.update(Categoria(id = id, nombre = nombre))
    }
}

class EliminarCategoriaUseCaseImpl(private val repo: CategoriaRepository) : EliminarCategoriaUseCase {
    override fun ejecutar(id: Int): Boolean {
        repo.findById(id) ?: throw NotFoundException("Categoria", id)
        return repo.delete(id)
    }
}
