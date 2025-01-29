package com.main.GuideAPI.security

import com.main.GuideAPI.token.TokenModel
import com.main.GuideAPI.token.TokenRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.function.Function
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
@RequiredArgsConstructor
class JwtAuthenticationFilter(
    @Autowired
    private val jwtService: JwtService,
    @Autowired
            private val userDetailsService: UserDetailsService,
    @Autowired
            private val tokenRepository: TokenRepository
) : OncePerRequestFilter() {


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        if (request.servletPath.contains("/api/v1/auth")) {
            filterChain.doFilter(request, response)
            return
        }
        val authHeader = request.getHeader("Authorization")
        val jwt: String
        val userEmail: String
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        jwt = authHeader.substring(7)
        userEmail = jwtService!!.extractUsername(jwt)
        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService!!.loadUserByUsername(userEmail)
            val isTokenValid = tokenRepository!!.findByToken(jwt)
                ?.map<Boolean>(Function { t: TokenModel? -> !t!!.expired && !t.revoked })
                ?.orElse(false)
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid!!) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}