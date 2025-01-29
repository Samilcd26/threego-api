package com.main.GuideAPI.auth

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor


@Data
@AllArgsConstructor
@NoArgsConstructor
data class AuthenticationResponse(

    @JsonProperty("access_token")
    val accessToken:String?=null,
    @JsonProperty("refresh_token")
    val refreshToken:String?=null,
)
