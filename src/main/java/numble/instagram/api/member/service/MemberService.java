package numble.instagram.api.member.service;

import lombok.RequiredArgsConstructor;
import numble.instagram.api.member.controller.request.MemberJoinDto;
import numble.instagram.api.member.controller.request.MemberJoinRequest;
import numble.instagram.api.member.service.mapper.MemberMapper;
import numble.instagram.common.exception.ConflictException;
import numble.instagram.domain.member.Member;
import numble.instagram.domain.member.MemberRepository;
import numble.instagram.domain.member.vo.EncodedPassword;
import numble.instagram.domain.member.vo.Identifier;
import numble.instagram.domain.memberprofile.MemberProfile;
import numble.instagram.domain.memberprofile.MemberProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberProfileRepository memberProfileRepository;

    @Transactional
    public void join(MemberJoinRequest memberJoinRequest) {
        MemberJoinDto memberJoinDto = MemberMapper.convertToMemberJoinDto(memberJoinRequest);

        checkIdentifierIsExist(memberJoinDto.identifier());
        checkNicknameIsExist(memberJoinDto.nickname());

        EncodedPassword encodedPassword = new EncodedPassword(memberJoinDto.password());

        MemberProfile memberProfile = memberJoinDto.toMemberProfile();
        Member member = memberJoinDto.toMember(memberProfile, encodedPassword);

        memberRepository.save(member);
    }

    private void checkIdentifierIsExist(Identifier identifier) {
        if (memberRepository.findByIdentifier(identifier).isPresent()) {
            throw new ConflictException("이미 존재하는 아이디입니다.");
        }
    }

    private void checkNicknameIsExist(String nickname) {
        if (memberProfileRepository.findByNickname(nickname).isPresent()) {
            throw new ConflictException("이미 존재하는 닉네임입니다.");
        }
    }


}
