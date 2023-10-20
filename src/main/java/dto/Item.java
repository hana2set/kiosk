package dto;

import Items.superobject.DetailMenu;
import lombok.Data;

@Data
public class Item {
    private DetailMenu menu;
    private boolean hasIce = true;
    private int count = 0;

    public int calculateItemPrice() {
        return (getMenu().getPrice() + (isHasIce() ? 1 : 0) * 500) * getCount();
    }
}
