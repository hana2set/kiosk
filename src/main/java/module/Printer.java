package module;

import Menus.detail.Item;
import Menus.main.ItemMenu;

public class Printer {
    private MenuBook menuBook = new MenuBook();             //메뉴

    public void menu() {
        System.out.println("[ 메뉴 ]");
        System.out.println(menuBook.getItemMenuTxt(0));
    }

    public void menu(Basket basket) {
        System.out.println("=======================================");
        System.out.println();
        System.out.println("[ 메뉴 ]");
        System.out.println(menuBook.getItemMenuTxt(0));
        if (basket.size() > 0) {
            System.out.println();
            System.out.println("[ 주문확인 ]");
            System.out.println(menuBook.getOrderMenuTxt(menuBook.itemMenusSize()));
        }
    }

    public void detailMenu(ItemMenu menu) {
        System.out.println("[ 상세 메뉴 목록 ]");
        System.out.println(menuBook.getItemMenuTxt(menu, 0));
    }

    public void addBasket(Item item) {
        System.out.println("=======================================");
        System.out.println();
        System.out.println(menuBook.getItemTxt(item));
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println();
        System.out.println("1. 확인       2. 취소       3.수량 선택");
    }

    public void checkCount() {
        System.out.println("주문할 수량을 입력해주세요.(1-99)");
    }

    public void checkIce() {
        System.out.println("얼음을 추가하시겠습니까? 추가요금이 500원 부가됩니다.");
        System.out.println();
        System.out.println("1. 추가       2. 추가 없음");
    }

    public void order(Basket basket) {
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println(" [ 주문 목록 ] ");
        System.out.println(basket.getListTxt());
        System.out.println();
        System.out.println(" [ 총액 ] ");
        System.out.println("₩ " + basket.getTotalPrice());
        System.out.println();
        System.out.println("1. 확인       2. 메뉴판");
    }

    public void cancel() {
        System.out.println("주문을 취소하시겠습니까?");
        System.out.println();
        System.out.println("1. 확인       2. 취소");
    }
}
