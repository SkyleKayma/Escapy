package fr.skyle.escapy.data.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.di.qualifiers.FirebaseDatabaseUrl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun providesDatabaseReference(
        @FirebaseDatabaseUrl firebaseDatabaseUrl: String
    ): DatabaseReference =
        Firebase.database(firebaseDatabaseUrl).reference

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth =
        Firebase.auth
}