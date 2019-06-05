package org.california.monopolserver.exceptions;

public class TransferableNotFoundException extends GameException {

    public TransferableNotFoundException(String uuid) {
        super("Transferable not found " + uuid);
    }

}
