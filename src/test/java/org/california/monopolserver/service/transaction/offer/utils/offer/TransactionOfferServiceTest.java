package org.california.monopolserver.service.transaction.offer.utils.offer;

import org.california.monopolserver.instance.transferable.town.Town;
import org.junit.Test;

import java.lang.reflect.Method;

public class TransactionOfferServiceTest {

    @Test
    public void test() {

        Class<?> serviceClass = PBankTransactionAddService.class;

        Method m = TransactionOfferService.getMethod(serviceClass, Town.class);

        System.out.println(m);


    }

}
