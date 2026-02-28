package com.williamsel.labkeep.features.nuevodispositivo.di

import com.williamsel.labkeep.features.nuevodispositivo.data.datasource.api.JsonPlaceHolderNuevoDispositivoApi
import com.williamsel.labkeep.features.nuevodispositivo.data.repositories.NuevoDispositivoRepositoryImpl
import com.williamsel.labkeep.features.nuevodispositivo.domain.repositories.NuevoDispositivoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NuevoDispositivoModule {

    @Provides
    @Singleton
    fun provideNuevoDispositivoApi(retrofit: Retrofit): JsonPlaceHolderNuevoDispositivoApi =
        retrofit.create(JsonPlaceHolderNuevoDispositivoApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NuevoDispositivoBindsModule {

    @Binds
    @Singleton
    abstract fun bindNuevoDispositivoRepository(
        impl: NuevoDispositivoRepositoryImpl
    ): NuevoDispositivoRepository
}