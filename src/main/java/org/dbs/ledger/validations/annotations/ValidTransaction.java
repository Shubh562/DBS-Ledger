package org.dbs.ledger.validations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.dbs.ledger.validations.validators.ValidTransactionValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidTransactionValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransaction {
    String message() default "Invalid transaction";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
