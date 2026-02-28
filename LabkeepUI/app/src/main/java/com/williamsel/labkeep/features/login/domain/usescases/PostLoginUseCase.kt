package com.williamsel.labkeep.features.login.domain.usescases

import com.williamsel.labkeep.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(correo: String, contrasena: String): Result<Unit> {
        return repository.login(correo, contrasena)
    }
}