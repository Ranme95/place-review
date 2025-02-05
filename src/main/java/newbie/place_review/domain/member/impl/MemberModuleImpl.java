package newbie.place_review.domain.member.impl;

import lombok.RequiredArgsConstructor;
import newbie.place_review.domain.member.Member;
import newbie.place_review.domain.member.MemberModule;
import newbie.place_review.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberModuleImpl implements MemberModule {

    private final MemberRepository memberRepository;

    @Override
    public Member save(String email, String nickname) {
        Member member = Member.builder()
                .email(email)
                .nickName(nickname)
                .build();

        return memberRepository.save(member);
    }

    @Override
    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    public Optional<Member> update(Long memberId, String nickname) {

        return memberRepository.findById(memberId).map(member -> {
            member.setNickName(nickname);

            return member;
        });
    }

    @Override
    public Optional<Member> getById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
