package Menus.detail;

import Menus.detail.DetailMenu;
import Menus.main.ItemMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Tea implements DetailMenu {
    CHAMOMILE("chamomile", 3000, "캐모마일"),
    EARLGREY("Earl Grey", 3800, "얼그레이"),
    GREEN("green", 3800, "녹차"),
    ROOIBOS("Rooibos", 300, "루이보스")
    ;

    private ItemMenu itemMenu;
    private String name;
    private int price;
    private String desc;

    Tea(String name, int price, String desc) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.itemMenu = ItemMenu.TEA;
    }
}
