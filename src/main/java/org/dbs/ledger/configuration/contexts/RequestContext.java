package org.dbs.ledger.configuration.contexts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Getter
@Setter
public class RequestContext {
    private String requestId;

    private Long requestArrivalTime;
}
