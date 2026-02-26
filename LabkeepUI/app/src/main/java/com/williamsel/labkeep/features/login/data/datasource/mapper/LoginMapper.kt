package com.williamsel.labkeep.features.login.data.datasource.mapper


import com.williamsel.labkeep.features.login.data.datasource.models.LoginDto
import com.williamsel.labkeep.features.login.domain.entities.Login

fun LoginDto.toDomain(): Login {
    return Login(
        email = email,
        password = password
    )
}