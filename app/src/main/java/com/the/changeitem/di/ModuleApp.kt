package com.the.changeitem.di
import android.app.Application
import androidx.room.Room
import com.the.changeitem.data.ChangeItemDao
import com.the.changeitem.data.ChangeItemDatabase
import com.the.changeitem.data.ChangeItemRepositoryImpl
import com.the.changeitem.domain.repository.ChangeItemRepository
import com.the.changeitem.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApp {
    @Provides
    @Singleton
    fun provideNoteDataBase(app: Application): ChangeItemDatabase {
        return Room.databaseBuilder(app, ChangeItemDatabase::class.java, ChangeItemDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(dataBase: ChangeItemDatabase): ChangeItemRepository {
        return ChangeItemRepositoryImpl(dataBase.changeItemDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(repository: ChangeItemRepository): ItemUseCase {
        return ItemUseCase(
            addItemUseCase = AddItemUseCase(repository),
            deleteItemUseCase = DeleteItemUseCase(repository),
            getItemsUseCase = GetItemsUseCase(repository),
            getItemUseCase = GetItemUseCase(repository)
        )
    }

}
