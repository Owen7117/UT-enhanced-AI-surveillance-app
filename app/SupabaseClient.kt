package com.owenoneil.aisecuritycamera.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth

object SupabaseClient {

    private const val SUPABASE_URL = "https://qrxtzzrwecagtvnnzzkv.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_vsovyHDBO9kpUKVec_fzkA_6-vw9tKi"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(io.github.jan.supabase.auth.Auth)
    }
}

