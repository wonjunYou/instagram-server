package api.auth.service;

import api.auth.controller.request.SignUpRequest;

import domain.member.Gender;
import domain.member.Member;
import domain.member.MemberRepository;
import domain.member.Password;
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
