package com.williamsel.labkeep.features.login.domain.repositories

import com.williamsel.labkeep.features.login.domain.entities.Login

interface LoginRepository {
    suspend fun login(email: String, password: String): Login
}