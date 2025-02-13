package newbie.place_review.api;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class VerificationApi {

    private final JavaMailSender javaMailSender;

    private final ValueOperations<String, Object> valueOperations;

    private final int EMAIL_VERIFICATION_TIMEOUT = 300;

    public VerificationApi(JavaMailSender javaMailSender, RedisTemplate<String, Object> redisTemplate) {
        this.javaMailSender = javaMailSender;
        this.valueOperations = redisTemplate.opsForValue();
    }

    /**
     * @param email
     * @return 인증코드 전송 성공: 201 Created<br> 인증코드 전송 실패: 500 Internal Server Error
     */
    public ApiResponse<Void> processEmailVerification(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();

        String verificationCode = getVerificationCode();
        String verificationCodeHtml = createVerificationCodeHtml(verificationCode);

        storeEmailAndVerificationCode(email, verificationCode);

        try {
            message.setSubject("[Place Review] 이메일 인증코드");
            message.setSentDate(Date.valueOf(LocalDate.now()));
            message.addRecipients(Message.RecipientType.TO, email);
            message.setText(verificationCodeHtml, "utf-8", "html");

            javaMailSender.send(message);

            return ApiResponse.of("인증코드가 전송 되었습니다.", HttpStatus.CREATED);
        } catch (MessagingException e) {
            log.error("인증코드 전송에 실패하였습니다.");

            return ApiResponse.of("인증코드 전송 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @return 인증코드 불일치: 401 Unauthorized<br>인증 성공: 200 OK<br>인증 진행 상태 아님: 400 Bad Request
     */
    public ApiResponse<Boolean> checkEmailVerificationCode(String email, String verificationCode) {
        Optional<String> optVerificationCode = Optional.ofNullable((String) valueOperations.get(email));

        return optVerificationCode.map(storedVerificationCode -> {
                                      if (!storedVerificationCode.equals(verificationCode)) {
                                          return ApiResponse.of("인증코드가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED, false);
                                      }

                                      valueOperations.getAndDelete(email);
                                      successfulEmailVerification(email);

                                      return ApiResponse.of("이메일 인증 완료", HttpStatus.OK, true);
                                  })
                                  .orElseGet(() -> ApiResponse.of("이메일 인증이 진행되지 않았습니다.", HttpStatus.BAD_REQUEST, false));
    }

    private String getVerificationCode() {
        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        return stringBuilder.toString();
    }

    private String createVerificationCodeHtml(String verificationCode) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<p>인증코드: <strong>");
        stringBuilder.append(verificationCode);
        stringBuilder.append("</strong></p>");

        return stringBuilder.toString();
    }

    private void storeEmailAndVerificationCode(String email, Object verificationCode) {
        valueOperations.set(email, verificationCode, EMAIL_VERIFICATION_TIMEOUT, TimeUnit.SECONDS);
    }

    private void successfulEmailVerification(String email) {
        valueOperations.set("!" + email, "verified", 30, TimeUnit.MINUTES);
    }
}
