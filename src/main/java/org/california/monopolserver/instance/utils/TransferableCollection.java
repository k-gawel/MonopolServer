package org.california.monopolserver.instance.utils;

import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.money.BankMoney;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.model.interfaces.Chargeable;
import org.california.monopolserver.model.interfaces.Transferable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class TransferableCollection extends HashSet<Transferable> implements Comparable<TransferableCollection>, GameInstance {

    private final String uuid = UUID.randomUUID().toString();
    private final Game game;

    public TransferableCollection(Game game) {
        this.game = game;
        setMoney(new Money(game));
    }

    public TransferableCollection(Player owner) {
        this.game = owner.getGame();

        Money money = owner instanceof Bank ?
                new BankMoney((Bank) owner) : new Money(owner, 0);
        super.add(money);
    }

    @Override
    public boolean contains(Object transferable) {
        return transferable instanceof Money ?
                getMoney().compareTo((Money) transferable) >= 0 : super.contains(transferable);
    }


    @Override
    public boolean remove(Object transferable) {
        if(transferable instanceof Money)
            return removeMoney((Money) transferable);

        return super.remove(transferable);
    }


    @Override
    public boolean add(Transferable transferable) {
        if(transferable instanceof Money)
            return addMoney((Money) transferable);
        if(transferable instanceof Discount)
            return addDiscount((Discount) transferable);
        return super.add(transferable);
    }


    public Optional<Transferable> get(String uuid) {
        return stream()
                .filter(t -> t.getUUID().equals(uuid))
                .findFirst();
    }


    public <T extends Transferable> Collection<T> get(Class<T> type) {
        return this.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }


    public <T extends Transferable> Optional<T> get(Class<T> type, String uuid) {
        return get(type).stream()
                .filter(t -> t.getUUID().equals(uuid))
                .findFirst();
    }


    public Money getMoney() {
        ///THIS
        return this.stream()
                .filter(t -> t instanceof Money)
                .map(t -> (Money) t)
                .findFirst()
                .orElse(null);
    }


    public boolean setMoney(Money money) {
        removeIf(t -> t instanceof Money);
        return addMoney(money);
    }


    private boolean addMoney(Money money) {
        if(this.getMoney() == null)
            super.add(money);
        else
            this.getMoney().add(money);
        return true;
    }


    private boolean removeMoney(Money money) {
        try {
            getMoney().subtract(money);
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }


    private boolean addDiscount(Discount discount) {
        this.getDiscount(discount.getChargeable()).ifPresent(this::remove);
        return super.add(discount);
    }


    public Optional<Discount> getDiscount(Chargeable chargeable) {
        return this.stream()
                .filter(t -> t instanceof Discount)
                .map(t -> (Discount) t)
                .filter(d -> d.getChargeable().equals(chargeable))
                .findFirst();
    }


    public Money getPrice() {
        int price = this.stream()
                .map(Transferable::getBasicPrice)
                .mapToInt(Money::intValue)
                .sum();

        return new Money(game, price);
    }


    @Override
    public int compareTo(TransferableCollection o) {
        return this.getPrice().compareTo(o.getPrice());
    }


    public Transferable getCheapestTransferable(Player sender, Player receiver) {
        return stream()
                .filter(t -> t.getBasicPrice().compareTo(new Money(game)) > 0)
                .sorted()
                .findFirst()
                .orElse(null);
    }


    @Override
    public Game getGame() {
        return null;
    }


    @Override
    public String getUUID() {
        return this.uuid;
    }


}
