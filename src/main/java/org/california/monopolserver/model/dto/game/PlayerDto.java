package org.california.monopolserver.model.dto.game;

import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.dto.InstanceDto;
import org.california.monopolserver.model.dto.transferable.MoneyDto;

public class PlayerDto extends InstanceDto {

    public String type;
    public String name;
    public String color;
    public MoneyDto money;

    public PlayerDto(Player player) {
        super(player);

        this.type = player instanceof Bank ? "bank" : "player";
        this.name = player.name;
        this.color = player.color;
        this.money = new MoneyDto(player.properties.getMoney());
    }

}
