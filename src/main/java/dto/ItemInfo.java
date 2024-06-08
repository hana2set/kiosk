package dto;

import Menus.main.ItemMenu;
import Menus.detail.Item;
import lombok.Data;

@Data
public class ItemInfo {
    private Item item;
    private boolean hasIce = true;
    private int count = 0;

    public int calculateItemPrice() {
        int iceWeight = (this.getItem().getItemMenu().equals(ItemMenu.COFFEE) || this.getItem().getItemMenu().equals(ItemMenu.TEA)) && isHasIce() ? 1 : 0;
        return (this.getItem().getPrice() + iceWeight * 500) * getCount();
    }
}
