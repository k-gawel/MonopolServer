package org.california.monopolserver.instance.executable.tour;

import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.player.Player;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class Tour extends AbstractGameInstance {

    public Player player;
    public int index;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public boolean dice_rolled = false;


    Tour(Player player, int index) {
        super(player.getGame());
        this.player = player;
        this.index = index;
        startTime = LocalDateTime.now();
        endTime = startTime.plusMinutes(2);
    }


    public Collection<Discount> cleanDiscounts() {
        Collection<Discount> expiredDiscounts = game.players.stream()
                .flatMap(p -> p.properties.get(Discount.class).stream())
                .filter(d -> d.getEndTour() >= index)
                .collect(Collectors.toSet());

        game.players.forEach(p -> {
            p.properties.removeIf(expiredDiscounts::contains);
        });

        return expiredDiscounts;
    }


}
