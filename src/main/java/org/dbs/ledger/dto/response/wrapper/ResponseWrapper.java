package org.dbs.ledger.dto.response.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.dbs.ledger.enums.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResponseWrapper<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorResponse> errors;

    private MetaResponse meta;

    private ResponseWrapper(T data, List<ErrorResponse> errors, MetaResponse meta) {
        this.data = data;
        this.errors = errors;
        this.meta = meta;
    }

    public static <T> ResponseWrapper<T> success(T data) {
        MetaResponse metaResponse = MetaResponse.builder()
                .status(ResponseStatus.SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseWrapper<>(data, null, metaResponse);
    }

    public static <T> ResponseWrapper<T> failure(List<ErrorResponse> errorResponses) {
        MetaResponse metaResponse = MetaResponse.builder()
                .status(ResponseStatus.FAILURE)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseWrapper<>(null, errorResponses, metaResponse);
    }

}
