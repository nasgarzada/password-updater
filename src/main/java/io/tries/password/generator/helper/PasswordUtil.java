package io.tries.password.generator.helper;


import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.ArrayList;

import static org.passay.AllowedCharacterRule.ERROR_CODE;

public final class PasswordUtil {

    public static String generatePassword(Integer passLength, Boolean azChars) {
        var generator = new PasswordGenerator();
        var ruleList = new ArrayList<CharacterRule>();

        ruleList.add(getLowerCaseRule());
        ruleList.add(getUpperCaseRule());
        ruleList.add(getDigitRule());
        ruleList.add(getSpecialCharRule());

        if (azChars.booleanValue())
            ruleList.add(getAzCharRule());

        return generator.generatePassword(passLength, ruleList);
    }

    private static CharacterRule getLowerCaseRule() {
        var lowerCaseChars = EnglishCharacterData.LowerCase;
        return new CharacterRule(lowerCaseChars);
    }

    private static CharacterRule getUpperCaseRule() {
        var upperCaseChars = EnglishCharacterData.UpperCase;
        var upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(1);
        return upperCaseRule;
    }

    private static CharacterRule getDigitRule() {
        var digitChars = EnglishCharacterData.Digit;
        var digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);
        return digitRule;
    }

    private static CharacterRule getSpecialCharRule() {
        var specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        var rule = new CharacterRule(specialChars);
        rule.setNumberOfCharacters(1);
        return rule;
    }

    private static CharacterRule getAzCharRule() {
        var azChars = new CharacterData() {

            @Override
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return "ÇŞƏĞÖÜçşəğöü";
            }
        };

        var azCharRule = new CharacterRule(azChars);
        azCharRule.setNumberOfCharacters(1);
        return azCharRule;
    }
}
