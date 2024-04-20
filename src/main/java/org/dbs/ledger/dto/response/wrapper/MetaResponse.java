package org.dbs.ledger.dto.response.wrapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.dbs.ledger.enums.ResponseStatus;
import org.dbs.ledger.util.Constants;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MetaResponse {
    private ResponseStatus status;

    @JsonFormat(pattern = Constants.RESPONSE_TIMESTAMP_FORMAT, shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;
}
