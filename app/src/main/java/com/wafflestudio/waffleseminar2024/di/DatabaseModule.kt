package com.wafflestudio.waffleseminar2024.di

import android.content.Context
import com.wafflestudio.waffleseminar2024.data.database.LocalMovieRepository
import com.wafflestudio.waffleseminar2024.data.database.MyDao
import com.wafflestudio.waffleseminar2024.data.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase {
        return MyDatabase.getDatabase(context)
    }

    @Provides
    fun provideMyDao(database: MyDatabase): MyDao {
        return database.myDao()
    }


    @Provides
    @Singleton
    fun provideLocalMovieRepository(myDao: MyDao): LocalMovieRepository {
        return LocalMovieRepository(myDao)
    }
}