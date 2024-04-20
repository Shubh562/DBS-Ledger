package org.dbs.ledger.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dbs.ledger.validations.annotations.CountryCode;

import java.util.regex.Pattern;

public class CountryCodeValidator implements ConstraintValidator<CountryCode, String> {
    private static final Pattern PATTERN = Pattern.compile("^\\+\\d{1,4}$");

    @Override
    public void initialize(CountryCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || PATTERN.matcher(value).matches();
    }
}
