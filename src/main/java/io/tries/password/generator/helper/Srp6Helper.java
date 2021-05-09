package io.tries.password.generator.helper;


import com.nimbusds.srp6.BigIntegerUtils;
import com.nimbusds.srp6.SRP6ClientSession;
import com.nimbusds.srp6.SRP6CryptoParams;
import com.nimbusds.srp6.SRP6ServerSession;
import com.nimbusds.srp6.SRP6VerifierGenerator;
import io.tries.password.generator.model.SaltAndVerifier;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Data
@Component
public class Srp6Helper {

    public SRP6CryptoParams CRYPTO_PARAMS = new SRP6CryptoParams(SRP6CryptoParams.N_256, SRP6CryptoParams.g_common,
            "SHA-256");

    public SRP6ClientSession generateSRP6ClientSession() {
        var srp6ClientSession = new SRP6ClientSession();
        srp6ClientSession.setXRoutine(SRP6ThinbusRoutines.getXRoutine());
        srp6ClientSession.setHashedKeysRoutine(SRP6ThinbusRoutines.getURoutine());
        srp6ClientSession.setClientEvidenceRoutine(SRP6ThinbusRoutines.getClientEvidenceRoutine());
        srp6ClientSession.setServerEvidenceRoutine(SRP6ThinbusRoutines.getServerEvidenceRoutine());
        return srp6ClientSession;
    }

    public SRP6ServerSession generateSRP6ServerSession() {
        var srp6ServerSession = new SRP6ServerSession(CRYPTO_PARAMS);
        srp6ServerSession.setClientEvidenceRoutine(SRP6ThinbusRoutines.getClientEvidenceRoutine());
        srp6ServerSession.setHashedKeysRoutine(SRP6ThinbusRoutines.getURoutine());
        srp6ServerSession.setServerEvidenceRoutine(SRP6ThinbusRoutines.getServerEvidenceRoutine());
        return srp6ServerSession;
    }

    public SRP6VerifierGenerator generateSRP6VerifierGenerator() {
        var srp6VerifierGenerator = new SRP6VerifierGenerator(CRYPTO_PARAMS);
        srp6VerifierGenerator.setXRoutine(SRP6ThinbusRoutines.getXRoutine());
        return srp6VerifierGenerator;
    }

    public BigInteger generateRandomSalt() {
        var gen = generateSRP6VerifierGenerator();
        return new BigInteger(1, gen.generateRandomSalt());
    }

    public String getSalt(String username, String password) {
        var gen = generateSRP6VerifierGenerator();
        return BigIntegerUtils.toHex(new BigInteger(1, gen.generateRandomSalt()));
    }

    public SaltAndVerifier getSaltAndVerifier(String username, String password) {
        var gen = generateSRP6VerifierGenerator();
        var salt = new BigInteger(1, gen.generateRandomSalt());
        var verifier = gen.generateVerifier(salt, username, password);
        return new SaltAndVerifier(BigIntegerUtils.toHex(salt), BigIntegerUtils.toHex(verifier));
    }
}

