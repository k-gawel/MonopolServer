package org.california.monopolserver.instance.game;

public class ColorPicker {

    enum color {
        GREEN("GREEN"),
        RED("RED"),
        BLUE("BLUE"),
        YELLOW("YELLOW"),
        PINK("PINK");

        private final String value;

        color(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static String getNextColor(Game game) {
        return color.values()[game.players.size()].getValue();
    }

}
