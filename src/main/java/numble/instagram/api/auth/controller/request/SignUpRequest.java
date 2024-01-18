 package numble.instagram.api.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;

@Getter
public class SignUpRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.", regexp = "^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$\")\n")
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;

    @NotNull(message = "비밀번호는 null일 수 없습니다.")
    private String password;

    @NotNull(message = "닉네임은 null일 수 없습니다.")
    @Size(max = 100)
    private String nickname;

    private String name;

    private String gender;

    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "전화번호의 형식이 올바르지 않습니다.")
    private String telephone;
}
