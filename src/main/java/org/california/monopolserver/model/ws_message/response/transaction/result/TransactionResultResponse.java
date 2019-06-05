package org.california.monopolserver.model.ws_message.response.transaction.result;

import org.california.monopolserver.instance.executable.transaction.TransactionResult;
import org.california.monopolserver.instance.transferable.money.BankMoney;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.model.interfaces.Identifiable;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.model.ws_message.response.transaction.TransactionResponse;

import java.util.Collection;
import java.util.stream.Collectors;

@JSONTypeInfo
public class TransactionResultResponse extends TransactionResponse {

    public String initiator;
    public String invited;

    public int initiator_money;
    public int invited_money;

    public Collection<String> initiator_offer;
    public Collection<String> invited_offer;

    public TransactionResultResponse(TransactionResult result) {
        super(result.transaction);

        System.out.println("TransactionResulRespone");
        System.out.println("INITIATOR MONEY " + result.initiatorOffer.getMoney());
        System.out.println("INVITED MONEY " + result.invitedOffer.getMoney() + " ARE INSTANCE OF BANK MONEY " + (result.invitedOffer.getMoney() instanceof BankMoney));


        this.initiator = result.initiator.getUUID();
        this.invited = result.invited.getUUID();


        this.initiator_money = result.initiatorOffer.getMoney().intValue();
        this.initiator_offer = offerToUuids(result.initiatorOffer);


        this.invited_money = result.invitedOffer.getMoney().intValue();
        this.invited_offer = offerToUuids(result.invitedOffer);

    }

    private Collection<String> offerToUuids(Collection<Transferable> offer) {
        return offer.stream()
                .filter(t -> !(t instanceof Money))
                .map(Identifiable::getUUID)
                .collect(Collectors.toSet());
    }


}
