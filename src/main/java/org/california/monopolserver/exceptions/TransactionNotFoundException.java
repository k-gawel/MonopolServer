package org.california.monopolserver.exceptions;

public class TransactionNotFoundException extends GameException {

    public TransactionNotFoundException(String uuid) {
        super("Transaction not found: " + uuid);
    }

}
