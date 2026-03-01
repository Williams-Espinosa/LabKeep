package com.labkeep.application.usecase

import com.labkeep.domain.model.*
import com.labkeep.domain.port.input.*
import com.labkeep.domain.port.output.UsuarioRepository
import org.mindrot.jbcrypt.BCrypt

class RegistrarUsuarioUseCaseImpl(
    private val repo: UsuarioRepository
) : RegistrarUsuarioUseCase {
    override fun ejecutar(correo: String, contrasena: String) {
        if (correo.isBlank()) throw ValidationException("El correo es obligatorio")
        if (contrasena.isBlank()) throw ValidationException("La contraseña es obligatoria")
        if (repo.existeCorreo(correo)) throw ConflictException("El correo ya está registrado")
        val hash = BCrypt.hashpw(contrasena, BCrypt.gensalt())
        repo.guardar(Usuario(correo = correo, contrasena = hash))
    }
}

class LoginUsuarioUseCaseImpl(
    private val repo: UsuarioRepository
) : LoginUsuarioUseCase {
    override fun ejecutar(correo: String, contrasena: String): Usuario {
        val usuario = repo.buscarPorCorreo(correo) ?: throw UnauthorizedException()
        if (!BCrypt.checkpw(contrasena, usuario.contrasena)) throw UnauthorizedException()
        return usuario
    }
}
