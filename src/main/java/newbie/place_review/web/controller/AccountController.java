package newbie.place_review.web.controller;

import lombok.RequiredArgsConstructor;
import newbie.place_review.api.ApiResponse;
import newbie.place_review.api.MemberAccountApi;
import newbie.place_review.dto.MemberDto;
import newbie.place_review.dto.SignUpDto;
import newbie.place_review.web.handler.AccountModelHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final MemberAccountApi memberAccountApi;

    private final AccountModelHandler accountModelHandler;

    @GetMapping("/find/password")
    public String initFindPassword() {
        return "pages/account/find-password";
    }

    @GetMapping("/my-account")
    public String initMyAccount(Model model, RedirectAttributes redirectAttributes) {

        ApiResponse<? extends MemberDto> apiResponse = memberAccountApi.getCurrentMember();

        accountModelHandler.handleMyAccountView(apiResponse, model, redirectAttributes);

        if (apiResponse.getHttpStatus().isError()) {
            return "redirect:/feedback";
        }

        return "pages/account/my-account";
    }

    @GetMapping("/sign-in")
    public String initSignIn() {
        return "pages/account/sign-in";
    }

    @GetMapping("/sign-up")
    public String initSignUp() {
        return "pages/account/sign-up";
    }

    @PostMapping("/sign-up")
    public String processSignUp(SignUpDto signUpDto, RedirectAttributes redirectAttributes) {
        ApiResponse<Void> apiResponse = memberAccountApi.signUp(signUpDto);

        accountModelHandler.handleSignUpView(apiResponse, redirectAttributes);

        if(apiResponse.getHttpStatus().isError()) {
            return "redirect:/sign-up";
        }

        return "redirect:/sign-in";
    }

    @GetMapping("/sign-up/options")
    public String initSignUpOptions() {
        return "pages/account/sign-up-options";
    }
}
