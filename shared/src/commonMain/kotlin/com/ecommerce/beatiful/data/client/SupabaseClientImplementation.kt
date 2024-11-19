package com.ecommerce.beatiful.data.client

import Ecommerce_Beatiful.shared.BuildConfig
import com.ecommerce.beatiful.data.client.contracts.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient

class  SupabaseClientImplementation(): SupabaseClient {
    private val supabaseKey = BuildConfig.API_KEY



    override var supabase = createSupabaseClient(
        supabaseUrl = "https://mkdumdfdkznohqxegeor.supabase.co",
        supabaseKey = supabaseKey
    ) {
        install(Auth) {
            alwaysAutoRefresh = false
        }
    }
}

