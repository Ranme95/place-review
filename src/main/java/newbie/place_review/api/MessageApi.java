package newbie.place_review.api;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;

@Log4j2
@Service
@RequiredArgsConstructor
public class MessageApi {

    private final JavaMailSender javaMailSender;

    public void sendVerificationCodeTo(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setSubject("[Place Review] 이메일 인증코드");
            message.setSentDate(Date.valueOf(LocalDate.now()));
            message.addRecipients(Message.RecipientType.TO, email);

            String verificationCode = getVerificationCode();

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<p>인증코드: <strong>");
            stringBuilder.append(verificationCode);
            stringBuilder.append("</strong></p>");

            message.setText(stringBuilder.toString(), "utf-8", "html");

            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.error("인증코드 전송에 실패하였습니다.");
        }
    }

    private String getVerificationCode() {
        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }

        return stringBuilder.toString();
    }
}
