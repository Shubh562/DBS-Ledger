package org.dbs.ledger.service.impl;

import managers.jwt.impl.JwtAccessTokenManager;
import managers.jwt.models.JwtToken;
import org.dbs.ledger.dto.request.SignupRequest;
import org.dbs.ledger.dto.request.account.EmailSignInRequest;
import org.dbs.ledger.dto.request.account.MobileSignInRequest;
import org.dbs.ledger.dto.response.AccountDetailedResponse;
import org.dbs.ledger.dto.response.AccountResponse;
import org.dbs.ledger.dto.response.SignInResponse;
import org.dbs.ledger.dto.response.wrapper.ErrorResponse;
import org.dbs.ledger.enums.ErrorCode;
import org.dbs.ledger.enums.Status;
import org.dbs.ledger.exceptions.RestException;
import org.dbs.ledger.helper.AccountEntryHelper;
import org.dbs.ledger.helper.CurrencyHelper;
import org.dbs.ledger.helper.IdHelper;
import org.dbs.ledger.model.Account;
import org.dbs.ledger.model.AccountEntry;
import org.dbs.ledger.model.Currency;
import org.dbs.ledger.repository.AccountRepository;
import org.dbs.ledger.service.AccountService;
import org.dbs.ledger.transformer.AccountTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private final AccountTransformer accountTransformer;

    private final IdHelper idHelper;

    private final CurrencyHelper currencyHelper;

    private final AccountEntryHelper accountEntryHelper;

    private final JwtAccessTokenManager jwtAccessTokenManager;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountTransformer accountTransformer, IdHelper idHelper, CurrencyHelper currencyHelper, AccountEntryHelper accountEntryHelper, JwtAccessTokenManager jwtAccessTokenManager) {
        this.accountRepository = accountRepository;
        this.accountTransformer = accountTransformer;
        this.idHelper = idHelper;
        this.currencyHelper = currencyHelper;
        this.accountEntryHelper = accountEntryHelper;
        this.jwtAccessTokenManager = jwtAccessTokenManager;
    }

    @Override
    public SignInResponse signIn(MobileSignInRequest mobileSignInRequest) {
        Account account = accountRepository.findAccountByMobileAndStatus(accountTransformer.convertMobileRequestToModel(mobileSignInRequest.getMobileRequest()), Status.ACTIVE).orElseThrow(
                () -> new RestException(HttpStatus.UNAUTHORIZED, ErrorResponse.from(ErrorCode.INVALID_CREDENTIALS))
        );
        if (!account.getPassword().equals(mobileSignInRequest.getPassword())) {
            throw new RestException(HttpStatus.UNAUTHORIZED, ErrorResponse.from(ErrorCode.INVALID_CREDENTIALS));
        }
        JwtToken token = jwtAccessTokenManager.createToken(accountTransformer.convertAccountToJwtPayload(account));
        return accountTransformer.convertTokenToSignInResponse(token);
    }

    @Override
    public SignInResponse signIn(EmailSignInRequest emailSignInRequest) {
        Account account = accountRepository.findAccountByEmailAndStatus(accountTransformer.convertEmailRequestToModel(emailSignInRequest.getEmailRequest()), Status.ACTIVE).orElseThrow(
                () -> new RestException(HttpStatus.UNAUTHORIZED, ErrorResponse.from(ErrorCode.INVALID_CREDENTIALS))
        );
        if (!account.getPassword().equals(emailSignInRequest.getPassword())) {
            throw new RestException(HttpStatus.UNAUTHORIZED, ErrorResponse.from(ErrorCode.INVALID_CREDENTIALS));
        }
        JwtToken token = jwtAccessTokenManager.createToken(accountTransformer.convertAccountToJwtPayload(account));
        return accountTransformer.convertTokenToSignInResponse(token);
    }

    @Override
    public AccountResponse getAccount(String accountId) {
        Account account = accountRepository.findAccountByIdAndStatus(accountId, Status.ACTIVE).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, ErrorResponse.from(ErrorCode.ACCOUNT_NOT_FOUND))
        );
        Currency currency = currencyHelper.getCurrencyByName(account.getCurrencyName()).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, ErrorResponse.from(ErrorCode.CURRENCY_NOT_ACTIVE))
        );
        return accountTransformer.convertModelToResponse(account, currency);
    }

    @Override
    public AccountResponse signupAccount(SignupRequest signupRequest) {
        Account account = accountTransformer.convertSignupRequestToModel(signupRequest);
        account.setId(idHelper.getNextId());
        account.setStatus(Status.ACTIVE);
        Currency currency = currencyHelper.getCurrencyByName(account.getCurrencyName()).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, ErrorResponse.from(ErrorCode.CURRENCY_NOT_ACTIVE))
        );
        try {
            Account createdAccount = accountRepository.save(account);
            return accountTransformer.convertModelToResponse(createdAccount, currency);
        } catch (DuplicateKeyException duplicateKeyException) {
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorResponse.from(ErrorCode.ACCOUNT_ALREADY_EXIST));
        }
    }

    @Override
    public AccountDetailedResponse getAccountDetails(String accountId) {
        Account account = accountRepository.findAccountByIdAndStatus(accountId, Status.ACTIVE).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, ErrorResponse.from(ErrorCode.ACCOUNT_NOT_FOUND))
        );
        Currency currency = currencyHelper.getCurrencyByName(account.getCurrencyName()).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, ErrorResponse.from(ErrorCode.CURRENCY_NOT_ACTIVE))
        );
        List<AccountEntry> accountEntryList = accountEntryHelper.getAllAccountEntriesForAccountId(accountId);

        return accountTransformer.convertModelToDetailedResponse(account, currency, accountEntryList);
    }
}
