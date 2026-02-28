package com.williamsel.labkeep.features.login.data.repositories

import com.williamsel.labkeep.features.login.data.datasource.api.JsonPlaceHolderLoginApi
import com.williamsel.labkeep.features.login.data.datasource.models.LoginDto
import com.williamsel.labkeep.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: JsonPlaceHolderLoginApi
) : LoginRepository {

    override suspend fun login(correo: String, contrasena: String): Result<Unit> {
        return try {
            api.login(LoginDto(correo, contrasena))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}