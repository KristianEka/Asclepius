package com.dicoding.asclepius.di

import org.koin.dsl.module

val networkModule = module {

}

val databaseModule = module {

}

val repositoryModule = module {

}

val viewModelModule = module {

}

val appModule = listOf(
    networkModule,
    databaseModule,
    repositoryModule,
    viewModelModule
)