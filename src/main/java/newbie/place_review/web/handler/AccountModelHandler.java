package newbie.place_review.web.handler;

import newbie.place_review.api.ApiResponse;
import newbie.place_review.dto.MemberDto;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class AccountModelHandler {

    public void handleMyAccountView(ApiResponse<? extends MemberDto> apiResponse, Model model, RedirectAttributes redirectAttributes) {

        if (apiResponse.getHttpStatus().is2xxSuccessful()) {
            MemberDto memberDto = apiResponse.getData();

            model.addAttribute("memberId", memberDto.getMemberId());
            model.addAttribute("email", memberDto.getEmail());
            model.addAttribute("nickname", memberDto.getNickname());
        } else if(apiResponse.getHttpStatus().is4xxClientError()) {
            redirectAttributes.addAttribute("message", apiResponse.getMessage());
        }
    }
}
