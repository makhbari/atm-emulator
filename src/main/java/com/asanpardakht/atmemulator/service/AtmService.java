package com.asanpardakht.atmemulator.service;

import com.asanpardakht.atmemulator.Messages;
import com.asanpardakht.atmemulator.dto.*;
import com.asanpardakht.atmemulator.entity.Card;
import com.asanpardakht.atmemulator.entity.Transaction;
import com.asanpardakht.atmemulator.model.Currency;
import com.asanpardakht.atmemulator.model.RestResponse;
import com.asanpardakht.atmemulator.model.RestResponseType;
import com.asanpardakht.atmemulator.repository.CardRepository;
import com.asanpardakht.atmemulator.repository.TransactionRepository;
import com.asanpardakht.atmemulator.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AtmService {
    private final CardRepository cardRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final WithdrawChain firstWithdrawChain;

    private final TransactionRepository transactionRepository;

    public AtmService(CardRepository cardRepository, JwtTokenProvider jwtTokenProvider, TransactionRepository transactionRepository) {
        this.cardRepository = cardRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.transactionRepository = transactionRepository;

        this.firstWithdrawChain = new FiveHundredThousandWithdrawal();
        WithdrawChain secondWithdrawChain = new OneHundredThousandWithdrawal();
        WithdrawChain thirdWithdrawChain = new FiftyThousandWithdrawal();
        firstWithdrawChain.setNextChain(secondWithdrawChain);
        secondWithdrawChain.setNextChain(thirdWithdrawChain);
    }

    public ResponseEntity login(LoginRequestDto request) {
        Optional<Card> cardOptional = cardRepository.findByPanAndPin(request.getPan(), request.getPin());
        if (cardOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new RestResponse(RestResponseType.ERROR, Messages.INVALID_CUSTOMER.getValue()));
        }
        if (!cardOptional.get().getType().equals(Card.CardType.DEBIT)) {
            return ResponseEntity.badRequest().body(new RestResponse(RestResponseType.ERROR, Messages.INVALID_CARD_TYPE.getValue()));
        }
        if (cardOptional.get().getStatus().equals(Card.CardStatus.BLOCKED)) {
            return ResponseEntity.badRequest().body(new RestResponse(RestResponseType.ERROR, Messages.INVALID_CARD_STATUS.getValue()));
        }
        return ResponseEntity.ok(jwtTokenProvider.createToken(request.getPan(), request.getPin()));
    }

    public ResponseEntity getOperations() {
        OperationResponseDto operationResponseDto = new OperationResponseDto();
        List<OperationInfoDto> operations = new ArrayList<>();
        operations.add(new OperationInfoDto(AtmOperation.CHECK_BALANCE, AtmOperation.CHECK_BALANCE.getOperationTitle()));
        operations.add(new OperationInfoDto(AtmOperation.CASH_DEPOSIT, AtmOperation.CASH_DEPOSIT.getOperationTitle()));
        operations.add(new OperationInfoDto(AtmOperation.CASH_WITHDRAWAL, AtmOperation.CASH_WITHDRAWAL.getOperationTitle()));
        operationResponseDto.setOperations(operations);
        log.info("END getOperations");
        return ResponseEntity.ok(operationResponseDto);
    }

    @Transactional
    public ResponseEntity withdraw(CashWithdrawalRequestDto request) {
        if (request.getAmount() % 100000 != 0) {
            return ResponseEntity.badRequest().body(new RestResponse(RestResponseType.ERROR, Messages.INVALID_AMOUNT.getValue()));
        }

        String pan = getCustomerPan();
        Long balance = getCustomerWithdrawalBalance(pan);
        if (request.getAmount() > balance.intValue()) {
            return ResponseEntity.badRequest().body(new RestResponse(RestResponseType.ERROR, Messages.INSUFFICIENT_FUNDS.getValue()));
        }

        WithdrawResponseDto responseDto = new WithdrawResponseDto();
        firstWithdrawChain.withdraw(new Currency(request.getAmount()), responseDto);
        Transaction transaction = new Transaction();
        transaction.setTransactionType(Transaction.TransactionType.WITHDRAWAL);
        transaction.setPan(pan);
        transaction.setValue(-1 * Integer.valueOf(request.getAmount()).longValue());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transactionRepository.save(transaction);

        log.info("END withdraw");
        return ResponseEntity.ok(responseDto);
    }

    private Long getCustomerWithdrawalBalance(String pan) {
        return transactionRepository.findAllByPan(pan).stream().mapToLong(Transaction::getValue).sum();
    }

    private String getCustomerPan() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername().substring(0, 15);
    }

    public ResponseEntity getBalance() {
        log.info("START getBalance");
        return ResponseEntity.ok(getCustomerWithdrawalBalance(getCustomerPan()));
    }

    public ResponseEntity deposit(CashDepositRequestDto request) {
        String pan = getCustomerPan();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
        transaction.setPan(pan);
        transaction.setValue(Integer.valueOf(request.getAmount()).longValue());
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        transactionRepository.save(transaction);

        log.info("END deposit");
        return ResponseEntity.ok(new RestResponse(RestResponseType.SUCCESS, Messages.CASH_DEPOSIT_COMPLETED_SUCCESSFULY.getValue()));
    }
}
