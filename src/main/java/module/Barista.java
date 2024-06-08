package module;

import constant.Color;
import dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

public class Barista {
    static List<List<ItemInfo>> orderList = new ArrayList<>();
    public boolean makeDrink(List<ItemInfo> menus) throws InterruptedException {

        orderList.add(menus);

        System.out.println(
                "주문이 완료되었습니다!\n" +
                "\n" +
                "대기번호는 [ " + Color.ANSI_YELLOW +  orderList.size() + Color.ANSI_RESET + " ] 번 입니다.\n" +
                "(3초후 메뉴판으로 돌아갑니다.)"
        );

        Thread.sleep(3000);

        return true;
    }
}
