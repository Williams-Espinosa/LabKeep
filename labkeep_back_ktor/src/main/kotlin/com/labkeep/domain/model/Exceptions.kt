package com.labkeep.domain.model

sealed class DomainException(message: String) : RuntimeException(message)

class NotFoundException(entidad: String, id: Any)     : DomainException("$entidad con id=$id no encontrado")
class ConflictException(message: String)               : DomainException(message)
class ValidationException(message: String)             : DomainException(message)
class UnauthorizedException(message: String = "Credenciales inválidas") : DomainException(message)
