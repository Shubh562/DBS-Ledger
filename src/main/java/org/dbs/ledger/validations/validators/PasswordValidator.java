package org.dbs.ledger.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dbs.ledger.util.MessageConstants;
import org.dbs.ledger.validations.annotations.Password;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private final List<PatternWrapper> patternWrappers = new LinkedList<>();

    private int minLength;

    private int maxLength;

    @Override
    public void initialize(Password constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.maxLength = constraintAnnotation.maxLength();

        if (constraintAnnotation.requireLowercase()) {
            patternWrappers.add(
                    new PatternWrapper(
                            Pattern.compile(".*[a-z].*"),
                            MessageConstants.PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CASE_CHARACTER
                    )
            );
        }

        if (constraintAnnotation.requireUppercase()) {
            patternWrappers.add(
                    new PatternWrapper(
                            Pattern.compile(".*[A-Z].*"),
                            MessageConstants.PASSWORD_MUST_HAVE_AT_LEAST_ONE_UPPER_CASE_CHARACTER
                    )
            );
        }

        if (constraintAnnotation.requireDigit()) {
            patternWrappers.add(
                    new PatternWrapper(
                            Pattern.compile(".*\\d.*"), MessageConstants.PASSWORD_MUST_HAVE_AT_LEAST_ONE_DIGIT
                    )
            );
        }

        if (constraintAnnotation.requireSpecialChar()) {
            patternWrappers.add(
                    new PatternWrapper(
                            Pattern.compile(".*[-+_!@#$%^&*., ?].*"),
                            MessageConstants.PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHARACTER
                    )
            );
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        boolean isValid = true;

        for (PatternWrapper patternWrapper : patternWrappers) {
            if (!patternWrapper.pattern().matcher(value).matches()) {
                isValid = false;
                context.buildConstraintViolationWithTemplate(patternWrapper.message()).addConstraintViolation();
            }
        }

        if (value.length() < this.minLength) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(
                    String.format(
                            MessageConstants.PASSWORD_MUST_BE_OF_MINIMUM_LENGTH,
                            this.minLength
                    )
            )
                    .addConstraintViolation();
        }

        if (value.length() > this.maxLength) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(
                    String.format(
                            MessageConstants.PASSWORD_MUST_BE_OF_MAXIMUM_LENGTH,
                            this.maxLength
                    )
            )
                    .addConstraintViolation();
        }

        return isValid;
    }

    private record PatternWrapper(Pattern pattern, String message) {
    }
}
