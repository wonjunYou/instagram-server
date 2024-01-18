package numble.instagram.api.auth.controller;

import numble.instagram.api.auth.controller.request.SignUpRequest;
import numble.instagram.api.auth.service.AuthService;
import jakarta.validation.Valid;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {

    private AuthService authService;

    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);

        return ResponseEntity.ok().build();
    }
}
