package numble.instagram.api.auth.controller.dto;

import numble.instagram.domain.member.vo.Identifier;
import numble.instagram.domain.member.vo.Password;

public record LoginDto(
    Identifier identifier,
    Password password
) {

}
