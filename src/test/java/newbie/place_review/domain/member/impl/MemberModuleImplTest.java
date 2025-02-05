package newbie.place_review.domain.member.impl;

import newbie.place_review.domain.member.Member;
import newbie.place_review.domain.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberModuleImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberModuleImpl memberModule;


    @Test
    @DisplayName("새로운 멤버 저장")
    void New_member_save_account() {

        //given
        Member member = mock(Member.class);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        //then
        assertEquals(member, memberModule.save("이메일", "주소"));
    }

    @Test
    @DisplayName("멤버 정보 수정 실패")
    void Member_update_info() {

        //given
        Member member = mock(Member.class);
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(DataRetrievalFailureException.class,
                () -> memberModule.update(1L, "nickname")
        );
    }

    @Test
    @DisplayName("멤버 계정 조회")
    void Find_a_member() {

        //given
        Member member = mock(Member.class);
        when(memberRepository.findById(1L)).thenReturn(Optional.ofNullable(member));

        //when
        //then
        assertEquals(member, memberModule.getById(1L).orElse(null));
    }
}