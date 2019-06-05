package org.california.monopolserver.service.transaction.result;

import org.california.monopolserver.instance.executable.transaction.*;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.model.ws_message.response.ResponseMessage;
import org.california.monopolserver.model.ws_message.response.transaction.result.TransactionResultResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionCommitService {

    private final CustomMessageTemplate messageTemplate;

    private final VoluntaryTransactionCommitService voluntaryTransactionCommitService;
    private final CompulsoryTransactionCommitService compulsoryTransactionCommitService;
    private final BankTransactionCommitService bankTransactionCommitService;

    @Autowired
    public TransactionCommitService(CustomMessageTemplate template, VoluntaryTransactionCommitService voluntaryTransactionCommitService, CompulsoryTransactionCommitService compulsoryTransactionCommitService, BankTransactionCommitService bankTransactionCommitService) {
        this.messageTemplate = template;
        this.voluntaryTransactionCommitService = voluntaryTransactionCommitService;
        this.compulsoryTransactionCommitService = compulsoryTransactionCommitService;
        this.bankTransactionCommitService = bankTransactionCommitService;
    }


    public void sendGift(Player player, Money gift) {
        BankGift bankGift = new BankGift(player, gift);
        commitTransaction(bankGift);
    }


    public void commitTransaction(Transaction transaction) {
        Transaction finalTransaction = gerFinalTransaction(transaction);

        TransactionResult result = commit(finalTransaction);

        transaction.getGame().removeTransaction();

        sendMessage(result);
    }


    private void sendMessage(TransactionResult result) {
        ResponseMessage message = new TransactionResultResponse(result);
        messageTemplate.sendMessage(message);
    }


    private Transaction gerFinalTransaction(Transaction transaction) {
        if(transaction instanceof BankTransaction)
            return bankTransactionCommitService.getFinalTransaction((BankTransaction) transaction);
        else if(transaction instanceof CompulsoryTransaction)
            return compulsoryTransactionCommitService.getFinalTransaction((CompulsoryTransaction) transaction);
        else if(transaction instanceof VoluntaryTransaction)
            return voluntaryTransactionCommitService.getFinalTransaction((VoluntaryTransaction) transaction);
        else
            return transaction;
    }


    private TransactionResult commit(Transaction transaction) {
        TransactionResult result = new TransactionResult(transaction);

        commitForSide(transaction, transaction.getInitiator(), result);
        commitForSide(transaction, transaction.getInvited(), result);

        return result;
    }


    private void commitForSide(Transaction transaction, Player side, TransactionResult result) {
        transaction.getOffer(side).stream()
            .peek(t -> t.transfer(side, transaction.getOppositeSide(side)))
            .forEach(t -> result.add(side, t));
    }


}
