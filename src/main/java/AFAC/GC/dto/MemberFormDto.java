package AFAC.GC.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MemberFormDto {

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gachon\\.ac\\.kr$", message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, max = 100, message = "이름은 2자 이상 100자 이하로 입력해주세요.")
    private String name;

    @NotNull(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "전화번호는 10자리 또는 11자리 숫자여야 합니다.")
    private String phoneNumber;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotNull(message = "학번은 필수 입력 값입니다.")
    private String studentNumber;

    @NotBlank(message = "학과는 필수 입력 값입니다.")
    private String department;
}
