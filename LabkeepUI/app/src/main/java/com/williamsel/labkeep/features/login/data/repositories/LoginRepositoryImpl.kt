package com.williamsel.labkeep.features.login.data.repositories

import android.util.Log
import com.williamsel.labkeep.features.login.data.datasource.api.JsonPlaceHolderLoginApi
import com.williamsel.labkeep.features.login.data.datasource.mapper.toDomain
import com.williamsel.labkeep.features.login.data.datasource.models.LoginDto
import com.williamsel.labkeep.features.login.domain.entities.Login
import com.williamsel.labkeep.features.login.domain.repositories.LoginRepository


class LoginRepositoryImpl(
    private val api: JsonPlaceHolderLoginApi
) : LoginRepository {

    override suspend fun login(email: String, password: String): Login {
        val response = api.login(LoginDto(email, password))
        Log.d("LoginResponse", response.toString())
        return response.toDomain()
    }
}