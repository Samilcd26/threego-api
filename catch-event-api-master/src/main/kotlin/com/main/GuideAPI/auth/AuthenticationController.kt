package com.main.GuideAPI.auth

import com.main.GuideAPI.data.models.UserModel
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthenticationController(
    @Autowired
    private val service: AuthenticationService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest?
    ): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.register(request!!))
    }

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthenticationRequest?
    ): ResponseEntity<UserModel> {
        return ResponseEntity.ok(service.authenticate(request!!))
    }

    @PostMapping("/refresh-token")
    @Throws(IOException::class)
    fun refreshToken(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ) {
        service.refreshToken(request!!, response!!)
    }
}