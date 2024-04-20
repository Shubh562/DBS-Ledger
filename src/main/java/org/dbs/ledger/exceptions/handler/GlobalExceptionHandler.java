package org.dbs.ledger.exceptions.handler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.dbs.ledger.dto.response.wrapper.ErrorResponse;
import org.dbs.ledger.dto.response.wrapper.ResponseWrapper;
import org.dbs.ledger.enums.ErrorCode;
import org.dbs.ledger.exceptions.RestException;
import org.dbs.ledger.util.MessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { RestException.class })
    public ResponseEntity<ResponseWrapper<Void>> handleResponseException(RestException restException) {
        return buildErrorResponse(restException.getHttpStatus(), restException.getErrorResponses());
    }

    @ExceptionHandler(value = { Throwable.class })
    public ResponseEntity<ResponseWrapper<Void>> handleGenericThrowable(Throwable throwable) {
        log.error(
                String.format(
                        MessageConstants.GLOBAL_EXCEPTION_HANDLER_CAPTURE_MESSAGE,
                        throwable.getClass().getName()
                ),
                throwable
        );
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(errorResponse));
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ResponseWrapper<Void>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        List<ErrorResponse> errorResponses = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INPUT_VALIDATION_ERROR);
                    errorResponse.setDetail(
                            String.format(
                                    MessageConstants.VALIDATION_ERROR_MESSAGE_FORMAT,
                                    fieldError.getField(),
                                    fieldError.getDefaultMessage()
                            )
                    );
                    return errorResponse;
                })
                .toList();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorResponses);
    }

    @ExceptionHandler(value = { ValidationException.class })
    public ResponseEntity<ResponseWrapper<Void>> handleValidationException(ValidationException validationException) {
        if (validationException.getCause() instanceof ConstraintViolationException constraintViolationException) {
            return handleConstraintViolationException(constraintViolationException);
        }
        return handleGenericThrowable(validationException);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<ResponseWrapper<Void>> handleConstraintViolationException(
            ConstraintViolationException exception
    ) {
        List<ErrorResponse> errorResponses = exception.getConstraintViolations()
                .stream()
                .map(constraintViolation -> {
                    Path propertyPath = constraintViolation.getPropertyPath();
                    String leafNodeName = null;
                    for (Path.Node node : propertyPath) {
                        leafNodeName = node.getName();
                    }
                    ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INPUT_VALIDATION_ERROR);
                    errorResponse.setDetail(
                            String.format(
                                    MessageConstants.VALIDATION_ERROR_MESSAGE_FORMAT,
                                    leafNodeName,
                                    constraintViolation.getMessage()
                            )
                    );
                    return errorResponse;
                })
                .toList();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorResponses);
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ResponseEntity<ResponseWrapper<Void>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException httpMessageNotReadableException
    ) {
        ArrayList<ErrorResponse> errorResponses = new ArrayList<>();
        if (httpMessageNotReadableException.getCause() instanceof InvalidFormatException invalidFormatException) {
            errorResponses.add(handleInvalidFormatException(invalidFormatException));
        } else if (
            httpMessageNotReadableException.getMessage()
                    .startsWith(MessageConstants.REQUIRED_REQUEST_BODY_IS_MISSING)
        ) {
            ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INPUT_VALIDATION_ERROR);
            errorResponse.setDetail(MessageConstants.REQUIRED_REQUEST_BODY_IS_MISSING);
            errorResponses.add(errorResponse);
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorResponses);
    }

    @ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
    public ResponseEntity<ResponseWrapper<Void>> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception
    ) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INPUT_VALIDATION_ERROR);
        errorResponse.setDetail(
                String.format(
                        MessageConstants.UNSUPPORTED_MEDIA_TYPE_MESSAGE,
                        exception.getContentType(),
                        exception.getSupportedMediaTypes()
                )
        );
        return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(errorResponse));
    }

    private ErrorResponse handleInvalidFormatException(InvalidFormatException invalidFormatException) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.INPUT_VALIDATION_ERROR);
        Class<?> targetType = invalidFormatException.getTargetType();
        if (targetType != null && targetType.isEnum()) {
            List<JsonMappingException.Reference> path = invalidFormatException.getPath();
            String fieldName = path.getLast().getFieldName();
            errorResponse.setDetail(
                    String.format(
                            MessageConstants.ENUM_INVALID_CAST_MESSAGE,
                            fieldName,
                            Arrays.toString(targetType.getEnumConstants())
                    )
            );
        }
        return errorResponse;
    }

    private ResponseEntity<ResponseWrapper<Void>> buildErrorResponse(
            HttpStatus httpStatus,
            List<ErrorResponse> errorResponses
    ) {
        return ResponseEntity.status(httpStatus).body(ResponseWrapper.failure(errorResponses));
    }
}
