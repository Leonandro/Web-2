package com.blood.bloodbackend.controller;

import com.blood.bloodbackend.model.AuthenticationRequest;
import com.blood.bloodbackend.model.AuthenticationResponse;
import com.blood.bloodbackend.model.DTO.UserLoginResponseDTO;
import com.blood.bloodbackend.service.MyUserDetailService;
import com.blood.bloodbackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            @RequestHeader(value = "ClientID", required = false) String clientId) {

        if(clientId == null || !clientId.equals("bloodapp_98")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw  new BadCredentialsException("Usuário ou senha incorretos");
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(new UserLoginResponseDTO(userDetails.getUsername(), "BLOOD " + jwt)));
    }
}
