package org.dbs.ledger.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dbs.ledger.validations.annotations.MobileNumber;

import java.util.regex.Pattern;

public class MobileNumberValidator implements ConstraintValidator<MobileNumber, String> {
    private static final Pattern PATTERN = Pattern.compile("^\\d{6,14}$");

    @Override
    public void initialize(MobileNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || PATTERN.matcher(value).matches();
    }
}
