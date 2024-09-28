package com.ecommerce.beatiful.di

import app.cash.sqldelight.db.SqlDriver
import com.ecommerce.beatiful.data.client.AmazonProductClient
import com.ecommerce.beatiful.data.client.ApolloImplementation
import com.ecommerce.beatiful.data.client.IApolloClient
import com.ecommerce.beatiful.data.local.AmazonProductSearchResource
import com.ecommerce.beatiful.data.repository.AmazonSearchProductRepository
import com.ecommerce.beatiful.db.EcommerceDB
import com.ecommerce.beatiful.viewModel.AmazonSearchProductViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule,
        viewModelModule,
        repositoryModule,
        localResourceModule,
        driverSQLModule,
        coreDatabase
    )
}

//https://github.com/hlnstepanova/kmpizza-repo/blob/main/shared/src/commonMain/kotlin/dev/tutorial/kmpizza/local/RecipeLocalSource.kt
//nao esquecer o model Serializer
//para criar o adapter preciso importar la no sq o List que e do kotlin e a String
//ou seja os dados complexos do kotlin
//classe precisa ser no maisculo
private val coreDatabase = module {
    single {
        EcommerceDB(
            get(),

        )
    }
}

private val clientModule = module {
    single {  AmazonProductClient(get()) }
    factory<IApolloClient> { ApolloImplementation() }
    factory<ApolloImplementation> { ApolloImplementation() }
}

private val localResourceModule = module {
    single { AmazonProductSearchResource(get()) }
}

private val repositoryModule = module {
    single { AmazonSearchProductRepository() }

}


private  val viewModelModule = module {
    single { AmazonSearchProductViewModel() }
}
fun initKoin() = initKoin {

}