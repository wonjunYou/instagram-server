package numble.instagram.api.auth.service;

import numble.instagram.api.auth.controller.request.SignUpRequest;

import numble.instagram.member.Gender;
import numble.instagram.member.Member;
import numble.instagram.member.MemberRepository;
import numble.instagram.member.Password;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public void signUp(SignUpRequest signUpRequest) {

        Member member = Member.builder()
            .name(signUpRequest.getName())
            .email(signUpRequest.getEmail())
            .password(Password.of(signUpRequest.getPassword()))
            .nickname(signUpRequest.getNickname())
            .gender(Gender.valueOf(signUpRequest.getGender()))
            .telephone(signUpRequest.getTelephone())
            .build();

        memberRepository.save(member);
    }
}
