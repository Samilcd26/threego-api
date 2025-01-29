package com.main.GuideAPI.security


import com.main.GuideAPI.token.TokenModel
import com.main.GuideAPI.token.TokenRepository
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
@RequiredArgsConstructor
class LogoutService(
    @Autowired
    private val tokenRepository: TokenRepository
):LogoutHandler {


    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val authHeader = request.getHeader("Authorization")
        val jwt: String
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        jwt = authHeader.substring(7)
        val storedToken: TokenModel? = tokenRepository!!.findByToken(jwt)!!
            .orElse(null)
        if (storedToken != null) {
            storedToken.expired=true
            storedToken.revoked=true
            tokenRepository.save(storedToken)
            SecurityContextHolder.clearContext()
        }
    }
}