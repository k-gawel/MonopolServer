package org.california.monopolserver.model.ws_message.response.game;

import org.california.monopolserver.instance.executable.tour.Tour;
import org.california.monopolserver.model.dto.game.TourDto;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class NewTourResponse extends GameActionResponse {

    public TourDto new_tour;

    public NewTourResponse(Tour tour) {
        super(tour.getGame());
        this.new_tour = new TourDto(tour);
    }

}
