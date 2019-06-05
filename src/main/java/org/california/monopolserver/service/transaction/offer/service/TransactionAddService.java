package org.california.monopolserver.service.transaction.offer.service;

import org.california.monopolserver.exceptions.TransactionNotFoundException;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.transferable.town.Improvement;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.model.dto.transferable.TransactionOperations;
import org.california.monopolserver.model.interfaces.Chargeable;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.model.ws_message.request.transaction.action.TransactionActionRequest;
import org.california.monopolserver.model.ws_message.request.transaction.action.add.TransactionAddExistingRequest;
import org.california.monopolserver.model.ws_message.request.transaction.action.add.TransactionAddMoneyRequest;
import org.california.monopolserver.model.ws_message.request.transaction.action.add.TransactionAddNewDiscountRequest;
import org.california.monopolserver.model.ws_message.request.transaction.action.add.TransactionAddNewImprovementRequest;
import org.california.monopolserver.model.ws_message.response.transaction.actions.item.TransactionAddResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.california.monopolserver.service.game.GameRegistry;
import org.california.monopolserver.service.transaction.offer.utils.offer.TransactionOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TransactionAddService {

    private final CustomMessageTemplate messageTemplate;
    private final TransactionOfferService offerService;

    @Autowired
    public TransactionAddService(CustomMessageTemplate messageTemplate, TransactionOfferService offerService) {
        this.messageTemplate = messageTemplate;
        this.offerService = offerService;
    }


    @RequestProcessor(TransactionAddExistingRequest.class)
    public void addExistingTransferable(TransactionAddExistingRequest request) {
        GameRegistry.getByUuid(request.game)
                .getTransferable(request.transferable)
                .ifPresent(t -> add(request, t));
    }


    @RequestProcessor(TransactionAddMoneyRequest.class)
    public void addMoney(TransactionAddMoneyRequest request) throws TransactionNotFoundException {
        Game game = GameRegistry.getByUuid(request.game);

        Money money = new Money(game, request.amount);

        add(request, money);
    }


    @RequestProcessor(TransactionAddNewImprovementRequest.class)
    public void getNewImprovement(TransactionAddNewImprovementRequest request) {
        Game game = GameRegistry.getByUuid(request.game);
        Town town = game.getTransferable(request.town, Town.class)
                .orElseThrow(() -> new IllegalArgumentException("Town doesn't exists in game " + request.town));

        Improvement improvement = new Improvement(town);

        add(request, improvement);
    }


    @RequestProcessor(TransactionAddNewDiscountRequest.class)
    public void addNewDiscount(TransactionAddNewDiscountRequest request) {
        Game game = GameRegistry.getByUuid(request.game);

        Class<? extends Transferable> chargeableClass = request.chargeable_type.asTransferableClass();

        Chargeable chargeable = (Chargeable) game.getTransferable(request.chargeable, chargeableClass)
                .orElseThrow(() -> new IllegalArgumentException("Chargeable doesnt exists in game " + request.chargeable));

        Discount discount =  Discount.create(chargeable, request.value, request.end_tour, request.discount_type.toClass());

        add(request, discount);
    }


    private void add(TransactionActionRequest request, Transferable transferable) {
        Transaction transaction = GameRegistry.getTransaction(request.transaction);
        Player side = GameRegistry.getPlayerByUuid(request.side);

        add(transaction, side, transferable);
    }


    private void add(Transaction transaction, Player side, Transferable transferable) {
        offerService.add(transaction, side, transferable)
                .ifPresent(t -> sendResponse(transaction, side, t));
    }


    private void sendResponse(Transaction t, Player side, Transferable item) {
        Collection<TransactionOperations> operations = offerService.createInfo(t);
        TransactionAddResponse response = new TransactionAddResponse(t, side, item, operations);
        messageTemplate.sendMessage(response);
    }



}
