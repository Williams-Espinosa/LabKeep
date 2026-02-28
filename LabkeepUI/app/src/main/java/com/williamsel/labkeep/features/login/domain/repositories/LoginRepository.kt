package com.williamsel.labkeep.features.login.domain.repositories

interface LoginRepository {
    suspend fun login(correo: String, contrasena: String): Result<Unit>
}