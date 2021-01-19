package internetshop.scheduler;

import internetshop.model.VerificationToken;
import internetshop.service.EmailService;
import internetshop.service.UserService;
import internetshop.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    EmailService emailService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    UserService userService;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void checkEndExpireDate() throws MessagingException {
        List<VerificationToken> verificationTokens = verificationTokenService.findAllByNotExpireDate();
        if (!verificationTokens.isEmpty()) {
            for (VerificationToken token : verificationTokens) {
                int hoursLost = token.getExpiryDate().minusHours(LocalDateTime.now().getHour())
                                                     .minusMinutes(LocalDateTime.now().getMinute()).getHour();
                if (hoursLost < 1 && !token.getUser().isActive()) {
                    emailService.remindAboutConfirmRegistrationMail(token);
                }
            }

        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void removeNotConfirmedUsers() {
        List<VerificationToken> verificationTokens = verificationTokenService.findAllByExpireDate();
        if (!verificationTokens.isEmpty()) {
            for (VerificationToken token : verificationTokens) {
                if (!token.getUser().isActive()) {
                    verificationTokenService.deleteById(token.getId());
                    userService.deleteById(token.getUser().getId());
                }
            }

        }
    }


}
