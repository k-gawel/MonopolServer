package org.california.monopolserver.instance.game;

public class ColorPicker {

    static enum color {
        GREEN("#00e6ac"),
        RED("#e63900"),
        BLUE("#1a1aff"),
        YELLOW("#ffff1a"),
        PINK("#ff1aff");

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
