package com.main.GuideAPI.auth

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor


@Data
@AllArgsConstructor
@NoArgsConstructor
data class RegisterRequest(
    val firstname:String?=null,
    val lastname:String?=null,
    val email:String?=null,
    val password:String?=null,
)
