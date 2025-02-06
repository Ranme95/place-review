package newbie.place_review.module.member;

import java.util.Optional;

public interface MemberModule {
    public Member save(String email, String nickname);

    public Optional<Member> getById(Long memberId);

    public void deleteById(Long memberId);

    public Member update(Long memberId, String nickname);
}
