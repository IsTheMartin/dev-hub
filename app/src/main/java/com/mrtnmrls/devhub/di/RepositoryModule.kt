package com.mrtnmrls.devhub.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mrtnmrls.devhub.data.MockRepository
import com.mrtnmrls.devhub.data.MockRepositoryImpl
import com.mrtnmrls.devhub.data.MocksProvider
import com.mrtnmrls.devhub.data.MocksProviderImpl
import com.mrtnmrls.devhub.data.repository.AuthenticationRepository
import com.mrtnmrls.devhub.data.repository.AuthenticationRepositoryImpl
import com.mrtnmrls.devhub.esp8266.data.repository.Esp8266Repository
import com.mrtnmrls.devhub.esp8266.data.repository.Esp8266RepositoryImpl
import com.mrtnmrls.devhub.domain.mapper.CurrentUserMapper
import com.mrtnmrls.devhub.todolist.data.repository.InMemoryTaskRepositoryImpl
import com.mrtnmrls.devhub.todolist.domain.repository.TaskRepository
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
}
