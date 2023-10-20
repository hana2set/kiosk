package Menus.detail;

import Menus.main.ItemMenu;
import Menus.main.Menu;

public interface DetailMenu extends Menu {
    ItemMenu itemMenu = null;
    ItemMenu getItemMenu();
    int price = 0;
    int getPrice();
}
