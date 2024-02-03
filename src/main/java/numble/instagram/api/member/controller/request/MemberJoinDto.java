package numble.instagram.api.member.controller.request;

import numble.instagram.domain.member.GenderType;
import numble.instagram.domain.member.Member;
import numble.instagram.domain.member.vo.EncodedPassword;
import numble.instagram.domain.member.vo.Identifier;
import numble.instagram.domain.member.vo.Password;
import numble.instagram.domain.memberprofile.MemberProfile;

public record MemberJoinDto(
    Identifier identifier,
    Password password,
    String email,
    String nickname,
    String name,
    String phoneNumber,
    GenderType genderType
) {
    public MemberProfile toMemberProfile() {

        return MemberProfile.builder()
            .email(this.email)
            .nickname(this.nickname)
            .name(this.name)
            .genderType(this.genderType)
            .phoneNumber(this.phoneNumber)
            .build();
    }

    public Member toMember(MemberProfile memberProfile, EncodedPassword encodedPassword) {
        return Member.builder()
            .identifier(this.identifier)
            .password(encodedPassword)
            .memberProfile(memberProfile)
            .build();
    }
}