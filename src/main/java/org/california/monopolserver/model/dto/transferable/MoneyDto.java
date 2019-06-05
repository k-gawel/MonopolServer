package org.california.monopolserver.model.dto.transferable;

import org.california.monopolserver.instance.transferable.money.BankMoney;
import org.california.monopolserver.instance.transferable.money.Money;

public class MoneyDto extends TransferableDto {

    public final String instance_type = "money";

    public String owner;
    public int amount;
    public String type;

    public MoneyDto(Money money) {
        super(money);

        this.owner = money.getOwner() == null ? "" : money.getOwner().getUUID();
        this.amount = money.intValue();
        this.type = money instanceof BankMoney ? "bank" : "player";
    }

}
