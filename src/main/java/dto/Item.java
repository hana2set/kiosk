package dto;

import Menus.main.ItemMenu;
import Menus.detail.DetailMenu;
import lombok.Data;

@Data
public class Item {
    private DetailMenu menu;
    private boolean hasIce = true;
    private int count = 0;

    public int calculateItemPrice() {
        int iceWeight = (getMenu().getItemMenu().equals(ItemMenu.COFFEE) || getMenu().getItemMenu().equals(ItemMenu.TEA)) && isHasIce() ? 1 : 0;
        return (getMenu().getPrice() + iceWeight * 500) * getCount();
    }
}
