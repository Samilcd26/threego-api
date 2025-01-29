package com.main.GuideAPI.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.main.GuideAPI.data.models.UserModel
import com.main.GuideAPI.data.models.helperModels.userHelper.Role
import com.main.GuideAPI.data.repository.UserRepository
import com.main.GuideAPI.security.JwtService
import com.main.GuideAPI.token.TokenModel
import com.main.GuideAPI.token.TokenRepository
import com.main.GuideAPI.token.TokenType
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Service
@RequiredArgsConstructor
class AuthenticationService(
    @Autowired
    private val repository: UserRepository,
    @Autowired
    private val tokenRepository: TokenRepository,
    @Autowired
    private val passwordEncoder: PasswordEncoder,
    @Autowired
    private val jwtService: JwtService,
    @Autowired
    private val authenticationManager: AuthenticationManager,
) {

    fun register(request: RegisterRequest): AuthenticationResponse {

        var isHave = repository.findByEmail(request.email!!);
        if (!isHave.isEmpty) return AuthenticationResponse(null)
        val user: UserModel = UserModel(
            firstName = request.firstname,
            lastName = request.lastname,
            email = request.email,
            userPassword = passwordEncoder!!.encode(request.password),
            role = Role.USER
        )

        val savedUser = repository!!.save<UserModel>(user)
        val jwtToken = jwtService!!.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        saveUserToken(savedUser, jwtToken)
        return AuthenticationResponse(accessToken = jwtToken!!, refreshToken = refreshToken!!)
    }

    fun authenticate(request: AuthenticationRequest): UserModel {

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        var user = repository!!.findByEmail(request.email!!)
            .orElseThrow()
        val jwtToken = jwtService!!.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        revokeAllUserTokens(user)
        saveUserToken(user, jwtToken)
        user.accessToken=jwtToken!!
        user.refreshToken=refreshToken!!
        //AuthenticationResponse(accessToken = jwtToken!!, refreshToken = refreshToken!!)
        return user
    }

    private fun saveUserToken(user: UserModel, jwtToken: String?) {
        // Validate input
        if (jwtToken.isNullOrBlank()) {
            throw IllegalArgumentException("JWT Token cannot be null or blank.")
        }

        // Revoke all valid user tokens
        val validUserTokens = tokenRepository?.findAllValidTokenByUser(user.id)
        validUserTokens?.filterNotNull()?.forEach { token ->
            token.expired = true
            token.revoked = true
        }
        validUserTokens?.let {
            tokenRepository?.saveAll(it)
        }

        // Create and save the new token
        val newToken = TokenModel(
            user = user,
            token = jwtToken, // Non-null validated above
            tokenType = TokenType.BEARER,
            expired = false,
            revoked = false
        )
        tokenRepository?.save(newToken)
    }



    private fun revokeAllUserTokens(user: UserModel) {
        val validUserTokens: List<TokenModel?>? = tokenRepository?.findAllValidTokenByUser(user.id)
        if (validUserTokens!!.isEmpty()) return
        validUserTokens.forEach { token ->
            token!!.expired = true
            token.revoked = true
        }
        tokenRepository!!.saveAll(validUserTokens)
    }

    @Throws(IOException::class)
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val refreshToken: String
        val userEmail: String
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        refreshToken = authHeader.substring(7)
        userEmail = jwtService!!.extractUsername(refreshToken)
        if (userEmail != null) {
            val user = repository!!.findByEmail(userEmail)
                .orElseThrow()
            if (jwtService.isTokenValid(refreshToken, user)) {
                val accessToken = jwtService.generateToken(user)
                revokeAllUserTokens(user)
                saveUserToken(user, accessToken)
                val authResponse: AuthenticationResponse =
                    AuthenticationResponse(accessToken = accessToken, refreshToken = refreshToken)
                ObjectMapper().writeValue(response.outputStream, authResponse)
            }
        }
    }
}