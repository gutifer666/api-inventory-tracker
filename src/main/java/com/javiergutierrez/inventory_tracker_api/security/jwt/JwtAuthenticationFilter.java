package com.javiergutierrez.inventory_tracker_api.security.jwt;

import com.javiergutierrez.inventory_tracker_api.modules.users.application.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Si no hay header de autorización o no empieza con "Bearer ", continuamos con la cadena de filtros
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraemos el token JWT del header
        jwt = authHeader.substring(7);
        
        try {
            // Extraemos el username del token
            username = jwtUtil.extractUsername(jwt);
            
            // Si hay un username y no hay autenticación en el contexto de seguridad
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Cargamos los detalles del usuario
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                
                // Validamos el token
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    // Si el token es válido, creamos un token de autenticación
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    // Establecemos los detalles de la autenticación
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    
                    // Establecemos la autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Si hay algún error con el token, simplemente continuamos con la cadena de filtros
            logger.error("Error validating JWT token", e);
        }
        
        // Continuamos con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
