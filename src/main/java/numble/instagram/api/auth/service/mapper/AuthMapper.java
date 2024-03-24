package numble.instagram.api.auth.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import numble.instagram.api.auth.controller.dto.LoginDto;
import numble.instagram.api.auth.controller.dto.request.LoginRequest;
import numble.instagram.api.auth.controller.dto.request.ReissueTokenRequest;
import numble.instagram.api.auth.controller.dto.response.AuthenticationResponse;
import numble.instagram.domain.auth.EncryptedToken;
import numble.instagram.domain.member.vo.Identifier;
import numble.instagram.domain.member.vo.Password;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthMapper {

    public static LoginDto convertToLoginDto(final LoginRequest request) {
        return new LoginDto(new Identifier(request.identifier()), new Password(request.password()));
    }

    public static AuthenticationResponse convertToAuthenticationResponse(String refreshToken, String accessToken) {
        return new AuthenticationResponse(refreshToken, accessToken);
    }

    public static EncryptedToken convertToEncryptedToken(ReissueTokenRequest reissueTokenRequest) {
        return new EncryptedToken(reissueTokenRequest.refreshToken());
    }

}
