package com.edacy.edacy.controller;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// Dans AuthController
import org.springframework.security.core.Authentication;
import com.edacy.edacy.dto.JwtResponse;
import com.edacy.edacy.dto.LoginRequest;
import com.edacy.edacy.security.JwtUtils;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    // private final AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    /*
     * @PostMapping("/login")
     * public ResponseEntity<String> login(@RequestBody LoginRequest request) throws
     * AuthenticationException {
     * Authentication authentication = authenticationManager.authenticate(
     * new UsernamePasswordAuthenticationToken(
     * request.getUsernameOrEmail(),
     * request.getPassword()));
     * 
     * // Solution robuste avec vérification de type
     * if (authentication.getPrincipal() instanceof UserDetails) {
     * UserDetails userDetails = (UserDetails) authentication.getPrincipal();
     * String token = jwtUtils.generateJwtToken(userDetails);
     * return ResponseEntity.ok(token);
     * } else {
     * throw new RuntimeException("Principal is not of type UserDetails");
     * }
     * }
     */

    @PostMapping("/login")
    @Operation(summary = "Authentifie un utilisateur avec un nom d'utilisateur ou un email et retourne un JWT.")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request, BindingResult bindingResult) {
        // Log de validation
        System.out.println("LoginRequest: " + request);
        if (bindingResult.hasErrors()) {
            // Log des erreurs de validation
            bindingResult.getAllErrors()
                    .forEach(error -> System.out.println("Erreur de validation: " + error.getDefaultMessage()));
            return ResponseEntity.badRequest().body("Invalid request: " + bindingResult.getAllErrors());
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail().trim(),
                            request.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(jwt, jwtUtils.getJwtExpirationMs()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/test-json")
    public ResponseEntity<?> testJson(HttpServletRequest request) throws java.io.IOException {
        try {
            String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Corps brut: " + body);
            return ResponseEntity.ok(body);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error reading request: " + e.getMessage());
        }
    }

    @GetMapping("/generate-token")
    public ResponseEntity<?> generateTokenForTest() {
        UserDetails user = User.withUsername("admin")
                .password("Admin123!") // ce mot de passe est ignoré ici
                // .roles("ROLE_ADMIN")
                .build();

        String jwt = jwtUtils.generateJwtToken(user);
        return ResponseEntity.ok(new JwtResponse(jwt, jwtUtils.getJwtExpirationMs()));
    }

}
