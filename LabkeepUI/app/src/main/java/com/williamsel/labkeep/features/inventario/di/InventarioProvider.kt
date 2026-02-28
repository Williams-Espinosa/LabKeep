package com.williamsel.labkeep.features.inventario.di

import com.williamsel.labkeep.features.inventario.data.datasource.api.JsonPlaceHolderInventarioApi
import com.williamsel.labkeep.features.inventario.data.repositories.InventarioRepositoryImpl
import com.williamsel.labkeep.features.inventario.domain.repositories.InventarioRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InventarioModule {

    @Provides
    @Singleton
    fun provideInventarioApi(retrofit: Retrofit): JsonPlaceHolderInventarioApi =
        retrofit.create(JsonPlaceHolderInventarioApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class InventarioBindsModule {

    @Binds
    @Singleton
    abstract fun bindInventarioRepository(
        impl: InventarioRepositoryImpl
    ): InventarioRepository
}