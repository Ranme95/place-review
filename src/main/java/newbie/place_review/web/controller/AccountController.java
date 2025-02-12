package newbie.place_review.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

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

    @GetMapping("/sign-up/options")
    public String initSignUpOptions() {
        return "pages/account/sign-up-options";
    }
}
