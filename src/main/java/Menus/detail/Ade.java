package Menus.detail;

import Menus.detail.DetailMenu;
import Menus.main.ItemMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Ade implements DetailMenu {
    ORANGE("orange", 5500, "오렌지 에이드"),
    GRAPEFRUIT("grapefruit", 6000, "자몽 에이드"),
    LEMON("lemon", 6000, "레몬 에이드"),
    BLUELEMON("blue lemon", 6000, "블루레몬 에이드")
    ;

    private ItemMenu itemMenu;
    private String name;
    private int price;
    private String desc;

    Ade(String name, int price, String desc) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.itemMenu = ItemMenu.ETC;
    }
}
