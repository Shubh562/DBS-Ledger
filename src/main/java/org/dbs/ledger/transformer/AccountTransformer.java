package org.dbs.ledger.transformer;

import managers.jwt.models.JwtPayload;
import managers.jwt.models.JwtToken;
import org.dbs.ledger.annotation.Transformer;
import org.dbs.ledger.dto.request.SignupRequest;
import org.dbs.ledger.dto.request.common.EmailRequest;
import org.dbs.ledger.dto.request.common.MobileRequest;
import org.dbs.ledger.dto.response.AccountDetailedResponse;
import org.dbs.ledger.dto.response.AccountEntryResponse;
import org.dbs.ledger.dto.response.AccountResponse;
import org.dbs.ledger.dto.response.SignInResponse;
import org.dbs.ledger.dto.response.common.EmailResponse;
import org.dbs.ledger.dto.response.common.MobileResponse;
import org.dbs.ledger.enums.AccountBalanceOutputStatus;
import org.dbs.ledger.model.Account;
import org.dbs.ledger.model.AccountEntry;
import org.dbs.ledger.model.Currency;
import org.dbs.ledger.model.common.Email;
import org.dbs.ledger.model.common.Mobile;
import org.dbs.ledger.model.output.AccountBalance;
import org.dbs.ledger.model.output.AccountBalanceOutput;
import org.dbs.ledger.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Transformer
public class AccountTransformer {
    private final CommonTransformer commonTransformer;

    @Autowired
    public AccountTransformer(CommonTransformer commonTransformer) {
        this.commonTransformer = commonTransformer;
    }

    public Mobile convertMobileRequestToModel(MobileRequest mobileRequest) {
        return commonTransformer.convertMobileRequestToModel(mobileRequest);
    }

    public Email convertEmailRequestToModel(EmailRequest emailRequest) {
        return commonTransformer.convertEmailRequestToModel(emailRequest);
    }

    public Account convertSignupRequestToModel(SignupRequest signupRequest) {
        return Account
                .builder()
                .accountType(signupRequest.getAccountType())
                .currencyName(signupRequest.getCurrencyName())
                .name(signupRequest.getName())
                .profileUrl(signupRequest.getProfileUrl())
                .email(convertEmailRequestToModel(signupRequest.getEmail()))
                .mobile(convertMobileRequestToModel(signupRequest.getMobile()))
                .password(signupRequest.getPassword())
                .accountBalance(0)
                .build();
    }

    public JwtPayload convertAccountToJwtPayload(Account account) {
        return JwtPayload.builder().sub(account.getId()).build();
    }

    public MobileResponse convertMobileToResponse(Mobile mobile) {
        return commonTransformer.convertModelToResponse(mobile);
    }

    public EmailResponse convertEmailToResponse(Email email) {
        return commonTransformer.convertModelToResponse(email);
    }

    public SignInResponse convertTokenToSignInResponse(JwtToken token) {
        return SignInResponse.builder().accessToken(token.getToken()).build();
    }

    public AccountResponse convertModelToResponse(Account account, Currency currency) {
        return AccountResponse
                .builder()
                .accountId(account.getId())
                .email(convertEmailToResponse(account.getEmail()))
                .mobile(convertMobileToResponse(account.getMobile()))
                .name(account.getName())
                .profileUrl(account.getProfileUrl())
                .accountBalance(account.getAccountBalance().doubleValue()/currency.getDecimalPlaces())
                .build();
    }


    public AccountBalanceOutput convertAccountToOutput(Account account) {
        return new AccountBalanceOutput(AccountBalanceOutputStatus.SUCCESS, convertAccountToAccountBalance(account));
    }

    public AccountBalance convertAccountToAccountBalance(Account account) {
        return new AccountBalance(account.getId(), account.getAccountBalance());
    }

    public void updateAccountBalance(Account account, Integer balanceToAdd) {
        account.setAccountBalance(account.getAccountBalance() + balanceToAdd);
    }

    public AccountDetailedResponse convertModelToDetailedResponse(Account account, Currency currency, List<AccountEntry> accountEntryList) {
        return AccountDetailedResponse
                .builder()
                .accountId(account.getId())
                .name(account.getName())
                .accountBalance(account.getAccountBalance().doubleValue()/currency.getDecimalPlaces())
                .accountEntries(StreamUtils.emptyIfNull(accountEntryList).map(accountEntry -> convertAccountEntryToDetail(accountEntry, currency)).toList())
                .build();
    }

    private AccountEntryResponse convertAccountEntryToDetail(AccountEntry accountEntry, Currency currency) {
        return AccountEntryResponse
                .builder()
                .toAccountId(accountEntry.getToAccountId())
                .transferType(accountEntry.getTransferType())
                .transferredAmount(accountEntry.getAbsoluteTransferredFunds().doubleValue()/currency.getDecimalPlaces())
                .build();
    }
}
