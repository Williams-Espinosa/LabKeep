package com.williamsel.labkeep.features.eliminardispositivo.di

import com.williamsel.labkeep.features.eliminardispositivo.data.datasource.api.JsonPlaceHolderEliminarDispositivoApi
import com.williamsel.labkeep.features.eliminardispositivo.data.repositories.EliminarDispositivoRepositoryImpl
import com.williamsel.labkeep.features.eliminardispositivo.domain.repositories.EliminarDispositivoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EliminarDispositivoModule {

    @Provides
    @Singleton
    fun provideEliminarDispositivoApi(retrofit: Retrofit): JsonPlaceHolderEliminarDispositivoApi =
        retrofit.create(JsonPlaceHolderEliminarDispositivoApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class EliminarDispositivoBindsModule {

    @Binds
    @Singleton
    abstract fun bindEliminarDispositivoRepository(
        impl: EliminarDispositivoRepositoryImpl
    ): EliminarDispositivoRepository
}