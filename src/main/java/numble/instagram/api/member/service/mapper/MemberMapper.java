package numble.instagram.api.member.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import numble.instagram.api.member.controller.request.MemberJoinDto;
import numble.instagram.api.member.controller.request.MemberJoinRequest;
import numble.instagram.domain.member.GenderType;
import numble.instagram.domain.member.vo.Identifier;
import numble.instagram.domain.member.vo.Password;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMapper {

    public static MemberJoinDto convertToMemberJoinDto(MemberJoinRequest request) {
        Identifier identifier = new Identifier(request.identifier());
        Password password = new Password(request.password());
        GenderType genderType = GenderType.valueOf(request.genderType());

        return new MemberJoinDto(identifier, password, request.email(), request.nickname(), request.name(),
            request.phoneNumber(), genderType);
    }
}
