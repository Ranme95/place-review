package newbie.place_review.domain.member.impl;

import newbie.place_review.domain.member.Member;
import newbie.place_review.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MemberModuleImplTest {

    @ExtendWith(MockitoExtension.class)

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberModuleImpl memberModule;

    private Member member;

    @BeforeEach
    void prepare() {
        member = Member.builder()
                .email("이메일")
                .nickName("닉네임")
                .build();
    }


    @Test
    void save() {

        //when
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        //then
        member.setId(1L);
        assertEquals(member, memberModule.save("이메일", "주소"));
    }

    @Test
    void update() {

        //when
        when(memberRepository.findById(1L)).thenReturn(Optional.ofNullable(member));
        //then
        assertEquals(member, memberModule.update(1L, "nickname").orElse(null));
    }

    @Test
    void getById() {

        //when
        when(memberRepository.findById(1L)).thenReturn(Optional.ofNullable(member));
        //then
        member.setId(1L);
        assertEquals(member, memberModule.getById(1L).orElse(null));
    }
}