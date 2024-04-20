package org.dbs.ledger.dto.response.wrapper;

import lombok.*;
import org.dbs.ledger.enums.ErrorCode;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse implements Serializable {
    private String errorCode;

    private String message;

    private String detail;

    private String help;

    public static ErrorResponse from(ErrorCode errorCode) {
        return from(errorCode, null, null);
    }

    public static ErrorResponse from(ErrorCode errorCode, String detail) {
        return from(errorCode, detail, null);
    }

    public static ErrorResponse from(ErrorCode errorCode, String detail, String help) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), detail, help);
    }
}
