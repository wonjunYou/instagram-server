package numble.instagram.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.instagram.api.member.controller.request.MemberJoinRequest;
import numble.instagram.api.member.service.mapper.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 가입 관련 요청")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    @Operation(description = "일반 회원 가입 요청을 보낸다.", summary = "유저 회원 가입")
    public ResponseEntity<Void> join(@RequestBody @Valid final MemberJoinRequest request) {
        memberService.join(request);

        return ResponseEntity.ok().build();
    }
}
