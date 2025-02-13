package newbie.place_review.rest;

import lombok.RequiredArgsConstructor;
import newbie.place_review.api.ApiResponse;
import newbie.place_review.api.VerificationApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationApi verificationApi;

    @PostMapping("/verification/email")
    public ResponseEntity<ApiResponse<Void>> processEmailVerification(@RequestParam("email") String email) {
        ApiResponse<Void> apiResponse = verificationApi.processEmailVerification(email);

        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PostMapping("/verification/email/check")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailVerification(
            @RequestParam("email") String email,
            @RequestParam("verificationCode") String verificationCode
    ) {
        ApiResponse<Boolean> apiResponse = verificationApi.checkEmailVerificationCode(email, verificationCode);

        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

}
