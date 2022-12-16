package fontys.s3.backend.business.impl.user;

import fontys.s3.backend.business.exception.InvalidCredentialsException;
import fontys.s3.backend.business.usecase.user.CreateUserUseCase;
import fontys.s3.backend.domain.request.CreateUserRequest;
import fontys.s3.backend.domain.response.CreateUserResponse;
import fontys.s3.backend.persistence.UserRepository;
import fontys.s3.backend.persistence.entity.RoleEnum;
import fontys.s3.backend.persistence.entity.UserEntity;
import fontys.s3.backend.persistence.entity.UserRoleEntity;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Set;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @SneakyThrows
    @Override
    public CreateUserResponse createUser(CreateUserRequest request, HttpServletRequest httpServletRequest){
        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            throw new InvalidCredentialsException();
        }
        String siteURL = getSiteURL(httpServletRequest);
        UserEntity saveUser = saveNewUser(request, siteURL);
        return CreateUserResponse.builder()
                .userId(saveUser.getId())
                .build();
    }

    UserEntity saveNewUser(CreateUserRequest request, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String randomCode = RandomString.make(64);

        UserEntity newUser = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .isVerified(false)
                .verificationCode(randomCode)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.USER)
                        .build()));

        sendVerificationEmail(newUser, siteURL);
        return userRepository.save(newUser);
    }

    private void sendVerificationEmail(UserEntity user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "FlyAwayN0tifications@outlook.com";
        String senderName = "Fly Away";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Fly Away.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/users/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}