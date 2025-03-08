package org.dawanow.dawanowapi.controllers;

import org.dawanow.dawanowapi.dto.auth.AuthenticationRequestDTO;
import org.dawanow.dawanowapi.dto.auth.AuthenticationResponseDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterRequestDTO;
import org.dawanow.dawanowapi.dto.user.UserRegisterResponseDTO;
import org.dawanow.dawanowapi.models.User;
import org.dawanow.dawanowapi.models.UserRole;
import org.dawanow.dawanowapi.repositories.UserRepository;
import org.dawanow.dawanowapi.security.JwtTokenUtil;
import org.dawanow.dawanowapi.security.UserPrincipal;
import org.dawanow.dawanowapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            // Use the new repository method to get the User entity
            User user = userRepository.findByUsername(userPrincipal.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String jwt = jwtTokenUtil.generateToken(userPrincipal, user.getId(), user.getUserRole(), user.getOwnerId());

            return ResponseEntity.ok(new AuthenticationResponseDTO(
                    jwt,
                    user.getId(),
                    user.getUsername(),
                    user.getUserRole(),
                    user.getOwnerId()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

    }
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> registerUser(@RequestBody UserRegisterRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserRole requesterRole = UserRole.Customer; // Default role
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            requesterRole = ((UserPrincipal) authentication.getPrincipal()).getRole();
        }
        UserRegisterResponseDTO response = userService.registerUser(request,requesterRole);
        return ResponseEntity.ok(response);
    }
}