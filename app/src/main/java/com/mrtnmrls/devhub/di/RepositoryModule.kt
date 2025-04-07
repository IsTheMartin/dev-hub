package com.mrtnmrls.devhub.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mrtnmrls.devhub.common.data.repository.ResourceProviderImpl
import com.mrtnmrls.devhub.common.domain.repository.ResourceProvider
import com.mrtnmrls.devhub.common.data.repository.MockRepository
import com.mrtnmrls.devhub.common.data.repository.MockRepositoryImpl
import com.mrtnmrls.devhub.common.data.repository.MocksProvider
import com.mrtnmrls.devhub.common.data.repository.MocksProviderImpl
import com.mrtnmrls.devhub.login.data.repository.AuthenticationRepository
import com.mrtnmrls.devhub.login.data.repository.AuthenticationRepositoryImpl
import com.mrtnmrls.devhub.esp8266.data.repository.Esp8266Repository
import com.mrtnmrls.devhub.esp8266.data.repository.Esp8266RepositoryImpl
import com.mrtnmrls.devhub.login.domain.mapper.CurrentUserMapper
import com.mrtnmrls.devhub.guessnumber.data.repository.GuessNumberRepositoryImpl
import com.mrtnmrls.devhub.guessnumber.domain.repository.GuessNumberRepository
import com.mrtnmrls.devhub.todolist.data.repository.InMemoryTaskRepositoryImpl
import com.mrtnmrls.devhub.todolist.domain.repository.TaskRepository
import com.mrtnmrls.devhub.webscraping.data.remote.WebScraperService
import com.mrtnmrls.devhub.webscraping.data.repository.WebScraperRepositoryImpl
import com.mrtnmrls.devhub.webscraping.domain.repository.WebScraperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideStringResourceProvider(context: Context): ResourceProvider {
        return ResourceProviderImpl(context)
    }

    @Provides
    @Singleton
    fun provideMockProvider(context: Context): MocksProvider {
        return MocksProviderImpl(context)
    }

    @Provides
    @Singleton
    fun provideMockRepository(mockProvider: MocksProvider): MockRepository {
        return MockRepositoryImpl(mockProvider)
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideCurrentUserMapper(): CurrentUserMapper {
        return CurrentUserMapper()
    }

    @Provides
    @Singleton
    fun provideEsp8266Repository(firebaseDatabase: FirebaseDatabase): Esp8266Repository {
        return Esp8266RepositoryImpl(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth,
        currentUserMapper: CurrentUserMapper
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth, currentUserMapper)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(): TaskRepository {
        return InMemoryTaskRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGuessNumberRepository(): GuessNumberRepository {
        return GuessNumberRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideWebScraperService(): WebScraperService {
        return WebScraperService()
    }

    @Provides
    @Singleton
    fun provideWebScraperRepository(webScraperService: WebScraperService): WebScraperRepository {
        return WebScraperRepositoryImpl(webScraperService)
    }
}
