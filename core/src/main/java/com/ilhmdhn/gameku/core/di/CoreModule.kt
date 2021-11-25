package com.ilhmdhn.gameku.core.di

import androidx.room.Room
import com.ilhmdhn.gameku.core.BuildConfig
import com.ilhmdhn.gameku.core.data.GameRepository
import com.ilhmdhn.gameku.core.data.source.local.LocalDataSource
import com.ilhmdhn.gameku.core.data.source.local.room.GameDatabase
import com.ilhmdhn.gameku.core.data.source.remote.RemoteDataSource
import com.ilhmdhn.gameku.core.data.source.remote.network.ApiService
import com.ilhmdhn.gameku.core.domain.repository.IGameRepository
import com.ilhmdhn.gameku.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<GameDatabase>().gameDao()}
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("ilhmdhn".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            GameDatabase::class.java, "Game.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = BuildConfig.HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, BuildConfig.CERTIFICATE_PINNING1)
            .add(hostname, BuildConfig.CERTIFICATE_PINNING2)
            .add(hostname, BuildConfig.CERTIFICATE_PINNING3)
            .add(hostname, BuildConfig.CERTIFICATE_PINNING4)
            .build()
        OkHttpClient.Builder()
            .addInterceptor ( HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { AppExecutors() }
    single<IGameRepository>{
        GameRepository(
            get(),
            get(),
            get()
        )
    }
}