package org.dbs.ledger.validations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.dbs.ledger.util.MessageConstants;
import org.dbs.ledger.validations.validators.PasswordValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Invalid password format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minLength() default MessageConstants.PASSWORD_MIN_LENGTH;

    int maxLength() default MessageConstants.PASSWORD_MAX_LENGTH;

    boolean requireUppercase() default true;

    boolean requireLowercase() default true;

    boolean requireDigit() default true;

    boolean requireSpecialChar() default true;
}
