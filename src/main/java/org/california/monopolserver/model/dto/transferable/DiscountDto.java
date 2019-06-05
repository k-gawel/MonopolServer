package org.california.monopolserver.model.dto.transferable;

import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.discount.FixedDiscount;
import org.california.monopolserver.instance.transferable.utility.Utility;

public class DiscountDto extends TransferableDto {

    public String discount_type;
    public String owner;
    public String chargeable_type;
    public String chargeable;
    public int value;
    public int end_tour;

    public DiscountDto(Discount discount) {
        super(discount);
        System.out.println("DISCOUNT DTO CREATE");
        this.owner = discount.getOwner().getUUID();
        this.end_tour = discount.getEndTour();
        this.value = discount.getValue();
        this.chargeable = discount.getChargeable().getUUID();

        this.discount_type = discount instanceof FixedDiscount ? "fixed_discount" : "percentage_discount";
        this.chargeable_type = discount.getChargeable() instanceof Utility ? "utility" : "town";
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "DiscountDto{" +
                "discount_type='" + discount_type + '\'' +
                ", owner='" + owner + '\'' +
                ", chargeable_type='" + chargeable_type + '\'' +
                ", chargeable='" + chargeable + '\'' +
                ", value=" + value +
                ", end_tour=" + end_tour +
                '}';
    }
}
