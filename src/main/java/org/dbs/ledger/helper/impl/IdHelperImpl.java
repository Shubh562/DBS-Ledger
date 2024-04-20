package org.dbs.ledger.helper.impl;

import org.dbs.ledger.annotation.Helper;
import org.dbs.ledger.helper.IdHelper;

import java.util.UUID;

@Helper
public class IdHelperImpl implements IdHelper {

    @Override
    public String getNextId() {
        return UUID.randomUUID().toString();
    }
}
