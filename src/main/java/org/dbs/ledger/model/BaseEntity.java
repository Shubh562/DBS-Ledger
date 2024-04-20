package org.dbs.ledger.model;

import lombok.Getter;
import lombok.Setter;
import org.dbs.ledger.enums.Status;
import org.dbs.ledger.util.MongoConstants;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
abstract class BaseEntity {
    @Id
    private String mongoId;

    @Field(MongoConstants.ID)
    private String id;

    @Field(MongoConstants.STATUS)
    private Status status;

    @Version
    @Field(MongoConstants.VERSION)
    private Integer version;

    @CreatedDate
    @Field(MongoConstants.CREATED_AT)
    private Date createdAt;

    @LastModifiedDate
    @Field(MongoConstants.UPDATED_AT)
    private Date updatedAt;

    @CreatedBy
    @Field(MongoConstants.CREATED_BY)
    private String createdBy;

    @LastModifiedBy
    @Field(MongoConstants.UPDATED_BY)
    private String updatedBy;
}
