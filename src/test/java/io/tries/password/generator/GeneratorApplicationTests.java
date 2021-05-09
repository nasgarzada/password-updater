package io.tries.password.generator;

import io.tries.password.generator.helper.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeneratorApplicationTests {

    @Test
    void contextLoads() {
        for (int i = 0; i < 20; i++) {
            System.out.println(PasswordUtil.generatePassword(8, false));

        }
    }

}
