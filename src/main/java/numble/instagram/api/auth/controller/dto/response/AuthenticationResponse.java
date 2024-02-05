package numble.instagram.api.auth.controller.dto.response;

public record AuthenticationResponse(
    String refreshToken,
    String accessToken
) {

}
