package com.pure.photoverse.config

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class FirebaseTokenFilter(
    private val firebaseApp: FirebaseApp,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = request.getHeader("Authorization")
        if (token != null && token.startsWith("Bearer ")) {
            val firebaseToken = token.substring(7)
            val auth = FirebaseAuth.getInstance(firebaseApp)
            val decodedToken = auth.verifyIdToken(firebaseToken)
            val uid = decodedToken.uid
            val email = decodedToken.email
            val name = decodedToken.name
            val profileImage = decodedToken.picture

            val principal =
                mapOf(
                    "uid" to uid,
                    "email" to email,
                    "name" to name,
                    "profileImage" to profileImage,
                )

            val authentication = UsernamePasswordAuthenticationToken(principal, null, emptyList())
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
