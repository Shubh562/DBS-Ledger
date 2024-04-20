package org.dbs.ledger.validations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.dbs.ledger.validations.validators.CountryCodeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CountryCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CountryCode {

    String message() default "Invalid country code format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
