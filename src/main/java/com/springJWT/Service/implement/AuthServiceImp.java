package com.springJWT.Service.implement;

import com.springJWT.Dto.LoginRequest;
import com.springJWT.Dto.SignupRequest;
import com.springJWT.Enum.RoleName;
import com.springJWT.Model.Rol;
import com.springJWT.Model.User;
import com.springJWT.Repository.RoleRepository;
import com.springJWT.Repository.UserRepository;
import com.springJWT.Security.JwtResponse;
import com.springJWT.Security.JwtUtils;
import com.springJWT.Service.AuthService;
import com.springJWT.Utils.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthServiceImp implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthServiceImp(AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e);
        }

    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username ya esta en uso", HttpStatus.BAD_REQUEST));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email ya esta en uso", HttpStatus.BAD_REQUEST));
        }

        // Crear nueva cuenta de usuario
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Rol> rols = new HashSet<>();

        if (strRoles == null) {
            Rol userRol = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
            rols.add(userRol);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Rol adminRol = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
                        rols.add(adminRol);

                        break;
                    default:
                        Rol userRol = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Rol no encontrado"));
                        rols.add(userRol);
                }
            });
        }

        user.setRols(rols);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado con exito", HttpStatus.OK));
    }


}
