package org.dbs.ledger.validations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.dbs.ledger.validations.validators.MobileNumberValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileNumber {

    String message() default "Invalid mobile number format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
