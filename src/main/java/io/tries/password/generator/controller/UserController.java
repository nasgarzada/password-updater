package io.tries.password.generator.controller;


import io.tries.password.generator.helper.PasswordUtil;
import io.tries.password.generator.helper.Srp6Helper;
import io.tries.password.generator.model.PasswordUpdateResponse;
import io.tries.password.generator.repo.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final Srp6Helper srp6Helper;
    private final AuthUserRepository userRepository;

    @PutMapping("user/password")
    public ResponseEntity<PasswordUpdateResponse> updatePassword(
            @RequestParam(value = "login", required = false, defaultValue = "fuadm") String login,
            @RequestParam(value = "azChars", required = false, defaultValue = "false") Boolean canAddAzChars,
            @RequestParam(value = "length", required = false, defaultValue = "8") Integer length
    ) {
        log.info("ActionLog.updatePassword.start - login: {}, azChars: {}, length: {}", login, canAddAzChars, length);
        var password = PasswordUtil.generatePassword(length, canAddAzChars);

        log.debug("{} : {}", login, password);

        var saltAndVerifier = srp6Helper.getSaltAndVerifier(login, password);

        var isUpdated = userRepository.updateUserPassword(saltAndVerifier.getSalt(), saltAndVerifier.getVerifier(), login);

        if (!isUpdated) {
            log.error("ActionLog.error - user not updated");
            throw new RuntimeException("User password not updated");
        }

        log.debug("Updated: {} : salt: {}, verifier:{}", login, saltAndVerifier.getSalt(), saltAndVerifier.getVerifier());

        log.info("ActionLog.updatePassword.end - login: {}, azChars: {}, length: {}", login, canAddAzChars, length);
        return ResponseEntity.ok(
                PasswordUpdateResponse.builder()
                        .password(password)
                        .build()
        );
    }
}
