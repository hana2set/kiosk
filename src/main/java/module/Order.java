package module;

import Menus.detail.Item;
import Menus.main.ItemMenu;
import constant.Pointer;
import dto.ItemBox;
import error.WrongInputException;

public class Order {
    private MenuBook menuBook = new MenuBook();     //메뉴
    private Printer print = new Printer();          //출력

    private ItemMenu selectMenu;                    //선택된 상세 메뉴
    private ItemBox selectItem = new ItemBox();     //선택된 상품
    
    private Basket basket = new Basket();            //장바구니

    public Pointer main(int num) throws WrongInputException {
        Pointer pointer;
        if (0 < num && num <= menuBook.itemMenusSize()) {
            // 상품 메뉴
            selectMenu = menuBook.getItemMenus(num - 1);
            pointer = print.detailMenu(selectMenu);
        } else if (basket.size() > 0 && num <= menuBook.itemMenusSize() + menuBook.orderMenusSize()) {
            // 주문 메뉴
            int orderIndex = num - menuBook.itemMenusSize() - 1;
            pointer = switch (menuBook.getOrderMenus(orderIndex)) {
                case ORDER -> print.order(basket);
                case CANCEL -> print.cancel();
            };
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return pointer;
    }

    public Pointer detail(int num) throws WrongInputException {
        Pointer pointer = Pointer.DETAIL;

        if (0 < num && num <= menuBook.itemsMenuMap.get(selectMenu).size()) {
            Item item = menuBook.itemsMenuMap.get(selectMenu).get(num - 1);
            selectItem.setItem(item);
            print.addBasket(item);
            pointer = Pointer.ADD;
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return pointer;
    }

    public Pointer add(int num) throws WrongInputException {
        Pointer pointer = Pointer.ADD;

        if (num == 1) {
            selectItem.setCount(selectItem.getCount() + 1);
            if (selectMenu == ItemMenu.COFFEE
                    || selectMenu == ItemMenu.TEA) {
                pointer = print.checkIce();
            } else {
                basket.add(selectItem);
                selectItem = new ItemBox();
                pointer = print.mainMenu(basket);
            }
        } else if (num == 2) {
            pointer = print.mainMenu(basket);
        } else if (num == 3) {
            pointer = print.checkCount();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return pointer;
    }

    public Pointer checkCount(int num) throws WrongInputException {
        Pointer pointer = Pointer.COUNTOPTION;

        if (0 < num && num < 100) {
            int totalCount = selectItem.getCount() + num;
            if (totalCount < 100) {
                selectItem.setCount(totalCount);
                if (selectMenu == ItemMenu.COFFEE
                        || selectMenu == ItemMenu.TEA) {
                    pointer = print.checkIce();
                } else {
                    basket.add(selectItem);
                    selectItem = new ItemBox();
                    pointer = print.mainMenu(basket);
                }
            } else {
                System.out.println("수량이 초과되었습니다.");
                System.out.println();
                pointer = print.addBasket(selectItem.getItem());
            }
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return pointer;
    }

    public Pointer checkIce(int num) throws WrongInputException {
        Pointer pointer = Pointer.ICEOPTION;

        if (num == 1) {
            selectItem.setHasIce(true);
            basket.add(selectItem);
            selectItem = new ItemBox();
            pointer = print.mainMenu(basket);
        } else if (num == 2) {
            selectItem.setHasIce(false);
            basket.add(selectItem);
            selectItem = new ItemBox();
            pointer = print.mainMenu(basket);
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return pointer;
    }

    public Pointer order(int num) throws WrongInputException, InterruptedException {
        Pointer pointer = Pointer.ORDER;

        if (num == 1) {
            basket.order();
            pointer = print.mainMenu(basket);
        } else if (num == 2) {
            pointer = print.mainMenu(basket);
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return pointer;
    }

    // 장바구니 초기화
    public Pointer cancel(int num) throws WrongInputException {
        Pointer pointer = Pointer.COUNTOPTION;

        if (num == 1) {
            basket.reset();
            pointer = print.mainMenu(basket);
        } else if (num == 2) {
            pointer = print.mainMenu(basket);
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return pointer;
    }


}
