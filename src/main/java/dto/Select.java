package dto;

import menus.Drink;
import menus.Menu;
import lombok.Data;

@Data
public class Select {
    private Drink drink;
    private boolean hasIce = true;
    private int count = 0;

    public int calculateItemPrice() {
        int iceWeight = (this.getDrink().getCategory().equals(Menu.COFFEE) || this.getDrink().getCategory().equals(Menu.TEA)) && isHasIce() ? 1 : 0;
        return (this.getDrink().getPrice() + iceWeight * 500) * getCount();
    }
}
