package numble.instagram.api.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberJoinRequest (

    @NotBlank(message = "아이디는 빈 값일 수 없습니다.")
    String identifier,

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    String password,

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    String email,

    @NotBlank(message = "닉네임은 빈 값일 수 없습니다.")
    @Size(max = 100)
    String nickname,

    String name,

    String genderType,

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 맞지 않습니다.")
    String phoneNumber
) {

}
