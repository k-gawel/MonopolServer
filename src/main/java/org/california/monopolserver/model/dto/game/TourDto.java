package org.california.monopolserver.model.dto.game;

import org.california.monopolserver.instance.executable.tour.Tour;

import java.sql.Timestamp;

public class TourDto {

    public String player;
    public int index;
    public String end_date;
    public boolean dice_rolled;

    public TourDto(Tour tour) {
        this.player = tour.player.getUUID();
        this.index = tour.index;
        this.end_date = Timestamp.valueOf(tour.endTime).toString();
        this.dice_rolled = tour.dice_rolled;
    }

}
