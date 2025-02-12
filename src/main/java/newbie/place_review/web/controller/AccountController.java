package newbie.place_review.web.controller;

import lombok.RequiredArgsConstructor;
import newbie.place_review.api.AccountApi;
import newbie.place_review.dto.SignUpDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountApi accountApi;

    @GetMapping("/find/password")
    public String initFindPassword() {
        return "pages/account/find-password";
    }

    @GetMapping("/my-account")
    public String initMyAccount() {
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
    public String processSignUp(SignUpDto signUpDto) {
        accountApi.signUp(signUpDto);

        return "redirect:/sign-in";
    }

    @GetMapping("/sign-up/options")
    public String initSignUpOptions() {
        return "pages/account/sign-up-options";
    }
}
