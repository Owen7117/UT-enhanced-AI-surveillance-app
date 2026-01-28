package com.owenoneil.aisecuritycamera.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.storage.SharedPreferencesStorage

//object SupabaseClient {
//
//    private const val SUPABASE_URL = "https://qrxtzzrwecagtvnnzzkv.supabase.co"
//    private const val SUPABASE_KEY = "sb_publishable_vsovyHDBO9kpUKVec_fzkA_6-vw9tKi"
//
//    val client = createSupabaseClient(
//        supabaseUrl = SUPABASE_URL,
//        supabaseKey = SUPABASE_KEY
//    ) {
//        install(Postgrest)
//
//        install(Auth) {
//            storage = SharedPreferencesStorage(context = appContext)
//            autoLoadFromStorage = true
//        }
//    }
//}

object Supabase {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://YOUR_PROJECT_ID.supabase.co",
        supabaseKey = "YOUR_ANON_KEY"
    ) {
        install(Auth)
        install(Postgrest)
        install(Realtime)
    }
}
