package com.example.nationfinal.utils

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inhhc2JuYW11YndpZGZhcHlhbnhjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzAxMTQwNzAsImV4cCI6MjA0NTY5MDA3MH0.PpwKJGdxacwGZnBroF0z8tQgaGU4EhEp7oOKBymtDaQ",
        supabaseUrl = "https://xasbnamubwidfapyanxc.supabase.co"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}