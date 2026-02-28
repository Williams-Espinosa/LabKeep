package com.williamsel.labkeep.features.login.domain.usescases

import com.williamsel.labkeep.features.login.domain.entities.Login
import com.williamsel.labkeep.features.login.domain.repositories.LoginRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Login {
        return repository.login(email, password)
    }
}