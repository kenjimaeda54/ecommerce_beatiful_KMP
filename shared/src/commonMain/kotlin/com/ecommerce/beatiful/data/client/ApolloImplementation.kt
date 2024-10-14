package com.ecommerce.beatiful.data.client

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.http.HttpHeader
import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import com.apollographql.apollo.network.http.DefaultHttpEngine
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class ApolloImplementation() : IApolloClient {

    override val apollo: ApolloClient
        get() = ApolloClient.Builder().serverUrl("https://graphql.canopyapi.co/")
            .addHttpInterceptor(ApolloInterceptors("aedb8689-8a90-4f0f-ae16-7c983d35664d"))
            .httpEngine(DefaultHttpEngine(timeoutMillis = 300000))
            .addInterceptor(LoggingApolloInterceptor()).build()

}


private class ApolloInterceptors(val token: String) : HttpInterceptor {
    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        return chain.proceed(request.newBuilder().addHeader("API-KEY", "$token").build())
    }
}

class LoggingApolloInterceptor : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        return chain.proceed(request).onEach { response ->
            if (response.exception != null) {
                 print("error request: ${response.exception}")
            }
        }
    }
}