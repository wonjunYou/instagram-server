package numble.instagram.api.auth.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import numble.instagram.api.auth.controller.dto.request.LoginRequest;
import numble.instagram.api.auth.controller.dto.request.ReissueTokenRequest;
import numble.instagram.api.auth.controller.dto.response.AuthenticationResponse;
import numble.instagram.api.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody @Valid LoginRequest request
    ) {
        AuthenticationResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reissue")
    public ResponseEntity<AuthenticationResponse> reissue(@RequestBody @Valid final ReissueTokenRequest request) {
        final AuthenticationResponse response = authService.reissueToken(request);

        return ResponseEntity.ok(response);
    }

}
