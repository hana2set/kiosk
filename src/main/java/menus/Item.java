package menus;

import lombok.*;

@Getter
public enum Item {
    ESPRESSO(Menu.COFFEE, "espresso", 3000, "에스프레소"),
    AMERICANO(Menu.COFFEE, "americano", 3000, "아메리카노"),
    CAFELATTE(Menu.COFFEE, "cafe latte", 3500, "카페라떼"),
    CAPPUCCINO(Menu.COFFEE, "cappuccino", 3500, "카푸치노"),
    VANILLALATTE(Menu.COFFEE, "vanilla latte", 3800, "아메리카노"),
    HAZELNUTLATTE(Menu.COFFEE, "hazelnut latte", 3800, "헤이즐럿라떼"),

    CHAMOMILE(Menu.TEA, "chamomile", 3000, "캐모마일"),
    EARLGREY(Menu.TEA, "Earl Grey", 3800, "얼그레이"),
    GREEN(Menu.TEA, "green", 3800, "녹차"),
    ROOIBOS(Menu.TEA, "Rooibos", 3000, "루이보스"),

    ORANGE(Menu.ETC, "orange", 5500, "오렌지 에이드"),
    GRAPEFRUIT(Menu.ETC, "grapefruit", 6000, "자몽 에이드"),
    LEMON(Menu.ETC, "lemon", 6000, "레몬 에이드"),
    BLUELEMON(Menu.ETC, "blue lemon", 6000, "블루레몬 에이드"),
    ;

    private Menu menu;
    private String name;
    private int price;
    private String desc;

    Item(Menu menu, String name, int price, String desc) {
        this.menu = menu;
        this.name = name;
        this.price = price;
        this.desc = desc;
    }

}
