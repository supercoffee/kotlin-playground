package com.bendaschel.kotlinplayground

import com.google.gson.annotations.SerializedName

/**
 *
 * {
    "access_token": Your access token,
    "token_type": "bearer",
    "expires_in": Unix Epoch Seconds,
    "scope": A scope string,
    }
 */
data class AuthResponse(@SerializedName("access_token") val access_token: String,
                        @SerializedName("token_type") val token_type: String,
                        @SerializedName("expires_in") val expires_in: String,
                        @SerializedName("scope") val scope: String)