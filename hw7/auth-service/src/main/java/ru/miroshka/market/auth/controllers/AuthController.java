package ru.miroshka.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.miroshka.market.api.dto.*;
import ru.miroshka.market.api.exceptions.AppError;
import ru.miroshka.market.auth.data.User;
import ru.miroshka.market.auth.servicies.UserService;
import ru.miroshka.market.auth.utils.JwtTokenUtil;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/{uuid}")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest,@PathVariable String uuid) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException exp) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),"Incorrect username or password"),
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        userService.reloadCarts(uuid,authRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration/{uuid}")
    public ResponseEntity<?> createAuthToken(@RequestBody RegistrationUserDto registrationUserDto,@PathVariable String uuid) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Пароли не совпадают"),HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Пользователь с таким именем уже существует"),HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setEmail(registrationUserDto.getEmail());
        user.setUserName(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        userService.createUser(user);
        UserDetails userDetails = userService.loadUserByUsername(registrationUserDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        userService.reloadCarts(uuid,user.getUserName());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/secured")
    public String helloSecurity() {
        return "Hello";
    }

    @GetMapping("/check_auth")
    public StringResponse checkAuth(Principal principal) {

        return new StringResponse(principal.getName());
    }

}
