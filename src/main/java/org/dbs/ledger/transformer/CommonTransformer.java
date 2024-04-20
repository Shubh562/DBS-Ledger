package org.dbs.ledger.transformer;

import org.dbs.ledger.annotation.Transformer;
import org.dbs.ledger.dto.request.common.EmailRequest;
import org.dbs.ledger.dto.request.common.MobileRequest;
import org.dbs.ledger.dto.response.common.EmailResponse;
import org.dbs.ledger.dto.response.common.MobileResponse;
import org.dbs.ledger.model.common.Email;
import org.dbs.ledger.model.common.Mobile;

@Transformer
public class CommonTransformer {

    public Email convertEmailRequestToModel(EmailRequest emailRequest) {
        return Email.builder().emailId(emailRequest.getEmailId()).build();
    }

    public Mobile convertMobileRequestToModel(MobileRequest mobileRequest) {
        return Mobile.builder().countryCode(mobileRequest.getCountryCode()).mobileNumber(mobileRequest.getMobileNumber()).build();
    }

    public MobileResponse convertModelToResponse(Mobile mobile) {
        return MobileResponse.builder().countryCode(mobile.getCountryCode()).mobileNumber(mobile.getMobileNumber()).build();
    }

    public EmailResponse convertModelToResponse(Email email) {
        return EmailResponse.builder().emailId(email.getEmailId()).build();
    }
}
