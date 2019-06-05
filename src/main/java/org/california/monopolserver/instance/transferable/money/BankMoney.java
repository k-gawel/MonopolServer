package org.california.monopolserver.instance.transferable.money;

import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;

public class BankMoney extends Money {

    private Bank owner;

    public BankMoney(Bank bank) {
        this.owner = bank;
    }

    @Override
    public int intValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int add(int amount) {
        return Integer.MAX_VALUE;
    }

    @Override
    public Money add(Money money) {
        return this;
    }

    @Override
    public int subtract(int amount) {
        return Integer.MAX_VALUE;
    }

    @Override
    public Money subtract(Money money) {
        return this;
    }

    @Override
    public int compareTo(Money money) {
        return 1;
    }

    @Override
    public Bank getOwner() {
        return this.owner;
    }

    @Override
    public void transfer(Player sender, Player receiver) throws IllegalStateException {
    }

}
