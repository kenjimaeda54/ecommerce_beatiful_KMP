package com.ecommerce.beatiful.data.client

import com.ecommerce.beatiful.data.client.contracts.SupabaseClient
import com.ecommerce.beatiful.util.DataOrException
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP


class SupabaseImplementaion(private val supabaseClient: SupabaseClient) : SupabaseClient by supabaseClient {


    suspend fun sendCodeOTP(userPhone: String): DataOrException<Boolean, Exception, Boolean> {
        return try {
            supabaseClient.supabase.auth.signInWith(OTP) {
                phone = userPhone
                createUser = true
            }
            DataOrException(data = true, exception = null, isLoading = false)
        } catch (e: Exception) {
            DataOrException(data = null, exception = e, isLoading = false)
        }
    }

    suspend fun verifyCodeOTP(
        userPhone: String,
        code: String
    ): DataOrException<Boolean, Exception, Boolean> {
        return try {
            supabaseClient.supabase.auth.verifyPhoneOtp(
                type = OtpType.Phone.SMS,
                phone = userPhone,
                token = code
            )
            DataOrException(data = true, exception = null, isLoading = false)
        } catch (e: Exception) {
            DataOrException(data = null, exception = e, isLoading = false)
        }
    }

}