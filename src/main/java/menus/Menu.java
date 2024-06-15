package menus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum Menu {
    COFFEE(Category.ITEM, "Item", "커피"),
    TEA(Category.ITEM, "Tea", "차"),
    ETC(Category.ITEM, "Fruit Juice & Ade", "과일주스 및 에이드"),

    ORDER(Category.ORDER, "Order", "장바구니 확인 후 주문합니다."),
    CANCEL(Category.ORDER, "Cancel", "취소"),
//    EXIT(Category.ORDER, "exit", "키오스그 끄기")
    ;

    Category category;
    String name;
    String desc;


    public enum Category {
        ITEM, //상품
        ORDER, //주문
        ;
    }

    public static Menu[] values(String category) {
        Category categoryEnum = Category.valueOf(category);
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getCategory() == categoryEnum)
                .toArray(Menu[]::new);
    }
}