package com.ecommerce.beatiful.data.client.SupabaseClientImplementation

import Ecommerce_Beatiful.shared.BuildConfig
import com.ecommerce.beatiful.data.client.SupabaseClient.SupabaseClient
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

