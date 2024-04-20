package org.dbs.ledger.service;

import org.dbs.ledger.dto.request.SignupRequest;
import org.dbs.ledger.dto.request.account.EmailSignInRequest;
import org.dbs.ledger.dto.request.account.MobileSignInRequest;
import org.dbs.ledger.dto.response.AccountDetailedResponse;
import org.dbs.ledger.dto.response.SignInResponse;
import org.dbs.ledger.dto.response.AccountResponse;

public interface AccountService {
    SignInResponse signIn(MobileSignInRequest mobileSignInRequest);

    SignInResponse signIn(EmailSignInRequest emailSignInRequest);

    AccountResponse getAccount(String accountId);

    AccountResponse signupAccount(SignupRequest signupRequest);

    AccountDetailedResponse getAccountDetails(String accountId);
}
