package com.owenoneil.aisecuritycamera.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.storage.SharedPreferencesStorage


object Supabase {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://qrxtzzrwecagtvnnzzkv.supabase.co",
        supabaseKey = "sb_publishable_vsovyHDBO9kpUKVec_fzkA_6-vw9tKi"
    ) {
        install(Auth)
        install(Postgrest)
        install(Realtime)
    }
}
