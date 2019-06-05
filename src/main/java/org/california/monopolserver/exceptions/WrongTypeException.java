package org.california.monopolserver.exceptions;

public class WrongTypeException extends GameException {

    public WrongTypeException(Class superClass, Object object) {
        super("Wrong type of " + superClass.toString() + ": " + object.getClass().toString());
    }

}
