package com.likelion.liontalk.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {
    val client = createSupabaseClient(
        supabaseUrl = "https://gmyzgbgvngdfqsjnjpsi.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdteXpnYmd2bmdkZnFzam5qcHNpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTM5NDU0MDQsImV4cCI6MjA2OTUyMTQwNH0.bRCteuWlLjSeLp2sGDBObZVmaqxZh1Nby7ssG5Ge4Po"
    ) {
        install(GoTrue)
        install(Postgrest)
    }
}