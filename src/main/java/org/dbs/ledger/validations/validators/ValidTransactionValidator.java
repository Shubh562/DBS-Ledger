package org.dbs.ledger.validations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dbs.ledger.dto.request.TransactionRequest;
import org.dbs.ledger.validations.annotations.ValidTransaction;

public class ValidTransactionValidator implements ConstraintValidator<ValidTransaction, TransactionRequest> {

    @Override
    public void initialize(ValidTransaction constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TransactionRequest transactionRequest, ConstraintValidatorContext constraintValidatorContext) {
        return switch (transactionRequest.getTransactionType()) {
            case TRANSFER -> transactionRequest.getToAccountId() != null;
            case WITHDRAW, DEPOSIT -> transactionRequest.getToAccountId() == null;
        };
    }
}
