package com.owenoneil.aisecuritycamera

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth


object SupabaseClientProvider {

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://qrxtzzrwecagtvnnzzkv.supabase.co",
        supabaseKey = "sb_publishable_vsovyHDBO9kpUKVec_fzkA_6-vw9tKi"
    ) {
        install(Postgrest)
        install(Auth)
    }
}