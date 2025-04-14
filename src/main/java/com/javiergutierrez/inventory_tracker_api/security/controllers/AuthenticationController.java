package com.javiergutierrez.inventory_tracker_api.security.controllers;

import com.javiergutierrez.inventory_tracker_api.modules.users.domain.MyUserDetails;
import com.javiergutierrez.inventory_tracker_api.security.jwt.JwtUtil;
import com.javiergutierrez.inventory_tracker_api.security.models.AuthenticationRequest;
import com.javiergutierrez.inventory_tracker_api.security.models.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        log.info("Authentication request for user: {}", request.getUsername());

        // Autenticamos al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Obtenemos los detalles del usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generamos el token JWT
        String jwt = jwtUtil.generateToken(userDetails);

        // Obtenemos los roles del usuario
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        log.info("User {} authenticated successfully with roles: {}", request.getUsername(), roles);

        // Construimos y devolvemos la respuesta
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwt)
                .username(userDetails.getUsername())
                .roles(roles)
                .build());
    }
}
