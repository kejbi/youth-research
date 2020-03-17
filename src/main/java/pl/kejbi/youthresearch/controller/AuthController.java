package pl.kejbi.youthresearch.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kejbi.youthresearch.model.User;
import pl.kejbi.youthresearch.security.JwtProvider;
import pl.kejbi.youthresearch.security.payloads.JwtResponse;
import pl.kejbi.youthresearch.security.payloads.LoginRequest;
import pl.kejbi.youthresearch.security.payloads.RegistrationRequest;
import pl.kejbi.youthresearch.service.AuthService;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public User registerUser(@RequestBody @Valid RegistrationRequest registrationRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("invalid registration input");
        }

        return authService.registerUser(
                registrationRequest.getUsername(),
                registrationRequest.getName(),
                registrationRequest.getSurname(),
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                registrationRequest.getSecret(),
                registrationRequest.isTutor()
        );
    }

    @PostMapping("/login")
    public JwtResponse loginUser(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("something gone wrong");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        JwtResponse response = new JwtResponse();
        response.setToken(token);
        response.setUsername(jwtProvider.getUsernameFromJwt(token));
        response.setRole(jwtProvider.getRoleFromJwt(token));

        return response;
    }
}
