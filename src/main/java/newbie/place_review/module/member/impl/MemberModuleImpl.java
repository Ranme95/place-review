package newbie.place_review.module.member.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import newbie.place_review.module.member.Member;
import newbie.place_review.module.member.MemberModule;
import newbie.place_review.module.member.MemberRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberModuleImpl implements MemberModule {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Member save(@NonNull String email, @NonNull String password, @NonNull String nickname) {

        Member member = Member.builder()
                              .email(email)
                              .password(passwordEncoder.encode(password))
                              .nickname(nickname)
                              .build();

        return memberRepository.save(member);
    }

    @Override
    public void deleteById(@NonNull Long memberId) {

        Optional<Member> optMember = memberRepository.findById(memberId);

        optMember.ifPresentOrElse(memberRepository::delete,
                () -> {
                    throw new DataRetrievalFailureException("삭제할 회원을 찾을 수 없습니다.");
                });
    }

    @Override
    public Member update(@NonNull Long memberId, @NonNull String email, @NonNull String password, @NonNull String nickname) {

        return memberRepository.findById(memberId).map(member -> {
            member.setNickname(nickname);
            member.setEmail(email);
            member.setPassword(passwordEncoder.encode(password));

            return member;
        }).orElseThrow(() -> new DataRetrievalFailureException("수정할 회원을 찾을 수 없습니다."));
    }

    @Override
    public Optional<Member> getById(@NonNull Long memberId) {
        return memberRepository.findById(memberId);
    }
}
