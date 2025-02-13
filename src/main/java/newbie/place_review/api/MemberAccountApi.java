package newbie.place_review.api;

import lombok.RequiredArgsConstructor;
import newbie.place_review.dto.MemberDto;
import newbie.place_review.dto.SignUpDto;
import newbie.place_review.module.member.impl.MemberModuleImpl;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAccountApi {

    private final PasswordEncoder passwordEncoder;

    private final MemberModuleImpl memberModule;

    public ApiResponse<Void> signUp(SignUpDto signUpDto) {
        String email = signUpDto.getEmail();
        String nickname = signUpDto.getNickname();
        String password = passwordEncoder.encode(signUpDto.getPassword());

        memberModule.save(email, password, nickname);

        return ApiResponse.of("회원가입 성공", HttpStatus.CREATED);
    }

    public ApiResponse<? extends MemberDto> getCurrentMember() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return memberModule.getByEmail(email)
                           .map(member -> {
                               MemberDto memberDto = MemberDto.builder()
                                                              .memberId(member.getId())
                                                              .email(member.getEmail())
                                                              .nickname(member.getNickname())
                                                              .build();

                               return ApiResponse.of("회원 정보를 성공적으로 불러왔습니다.", HttpStatus.OK, memberDto);
                           })
                           .orElseGet(() -> ApiResponse.of("회원 정보를 불러올 수 없습니다.", HttpStatus.NOT_FOUND, null));
    }
}
