package com.ecommerce.beatiful.di

import com.ecommerce.beatiful.data.client.AmazonProductClient
import com.ecommerce.beatiful.data.client.ApolloImplementation
import com.ecommerce.beatiful.data.client.IApolloClient
import com.ecommerce.beatiful.data.repository.AmazonSearchProductRepository
import com.ecommerce.beatiful.viewModel.AmazonSearchProductViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule,
        viewModelModule,
        repositoryModule

    )
}

private val clientModule = module {
    single {  AmazonProductClient(get()) }
    factory<IApolloClient> { ApolloImplementation() }
    factory<ApolloImplementation> { ApolloImplementation() }
}

private val repositoryModule = module {
    single { AmazonSearchProductRepository() }

}


private  val viewModelModule = module {
    single { AmazonSearchProductViewModel() }
}
fun initKoin() = initKoin {

}