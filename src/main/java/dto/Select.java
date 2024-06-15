package dto;

import menus.Item;
import menus.Menu;
import lombok.Data;

@Data
public class Select {
    private Item item;
    private boolean hasIce = true;
    private int count = 0;

    public int calculateItemPrice() {
        int iceWeight = (this.getItem().getMenu().equals(Menu.COFFEE) || this.getItem().getMenu().equals(Menu.TEA)) && isHasIce() ? 1 : 0;
        return (this.getItem().getPrice() + iceWeight * 500) * getCount();
    }
}
