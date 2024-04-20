package org.dbs.ledger.enums;

import lombok.Getter;
import lombok.ToString;
import org.dbs.ledger.util.Constants;

@Getter
@ToString
public enum ErrorCode {

    INTERNAL_SERVER_ERROR("001", "Internal Server Error"),
    INPUT_VALIDATION_ERROR("002", "Input Validation Error"),
    INVALID_JWT_TOKEN("003", "Invalid JWT token provided"),
    JWT_TOKEN_EXPIRED("004", "JWT token expired"),
    TOKEN_MUST_START_WITH_BEARER("005", "Token must start with Bearer"),
    BALANCE_NOT_AVAILABLE("007", "Required Balance Not Available"),
    TRANSFER_FAILED("008", "Account entry creation failed"),
    ACCOUNT_NOT_FOUND("009", "Account not found"),
    ACCOUNT_ALREADY_EXIST("010", "Account already exists"),
    INVALID_CREDENTIALS("011", "Invalid Credentials"),
    CURRENCY_NOT_ACTIVE("012", "Currency transaction has been disabled");

    private final String code;

    private final String message;

    ErrorCode(String code, String message) {
        this.code = Constants.ERROR_CODE_PREFIX + code;
        this.message = message;
    }
}
