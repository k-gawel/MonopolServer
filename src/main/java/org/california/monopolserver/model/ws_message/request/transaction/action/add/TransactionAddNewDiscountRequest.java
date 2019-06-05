package org.california.monopolserver.model.ws_message.request.transaction.action.add;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.discount.FixedDiscount;
import org.california.monopolserver.instance.transferable.discount.PercentageDiscount;
import org.california.monopolserver.instance.transferable.town.Town;
import org.california.monopolserver.instance.transferable.utility.Utility;
import org.california.monopolserver.model.interfaces.Chargeable;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class TransactionAddNewDiscountRequest extends TransactionAddRequest {

    public DiscountType discount_type;
    public DiscountChargeableType chargeable_type;
    public String chargeable;
    public int value;
    public int end_tour;

    public enum  DiscountType {

        @JsonProperty("fixed")
        FIXED(FixedDiscount.class),
        @JsonProperty("percentage")
        PERCENTAGE(PercentageDiscount.class);

        private final Class<? extends Discount> clazz;

        DiscountType(Class<? extends Discount> clazz) {
            this.clazz = clazz;
        }

        public Class<? extends Discount> toClass() {
            return this.clazz;
        }

    }


    public enum DiscountChargeableType {

        @JsonProperty("town")
        TOWN(Town.class),
        @JsonProperty("utility")
        UTILITY(Utility.class);

        private final Class<?> cClass;

        DiscountChargeableType(Class<?> cClass) {
            this.cClass = cClass;
        }

        public Class<? extends Chargeable> asChargeableClass() {
            return (Class<? extends Chargeable>) this.cClass;
        }

        public Class<? extends Transferable> asTransferableClass() {
            return (Class<? extends Transferable>) this.cClass;
        }

    }


}
