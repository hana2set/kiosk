package module;

import Menus.detail.Item;
import Menus.main.ItemMenu;
import constant.OrderLevel;
import dto.ItemBox;
import error.WrongInputException;

public class Order {
    private MenuBook menuBook = new MenuBook();     //메뉴
    private Printer print = new Printer();          //출력

    private ItemMenu selectMenu;                    //선택된 상세 메뉴
    private ItemBox selectItem = new ItemBox();     //선택된 상품
    
    private Basket basket = new Basket();            //장바구니

    public OrderLevel main(int num) throws WrongInputException {
        OrderLevel orderLevel;
        if (0 < num && num <= menuBook.itemMenusSize()) {
            // 상품 메뉴
            selectMenu = menuBook.getItemMenus(num - 1);
            orderLevel = print.detailMenu(selectMenu);
        } else if (basket.size() > 0 && num <= menuBook.itemMenusSize() + menuBook.orderMenusSize()) {
            // 주문 메뉴
            int orderIndex = num - menuBook.itemMenusSize() - 1;
            orderLevel = switch (menuBook.getOrderMenus(orderIndex)) {
                case ORDER -> print.order(basket);
                case CANCEL -> print.cancel();
            };
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel detail(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.DETAIL;

        if (0 < num && num <= menuBook.itemsMenuMap.get(selectMenu).size()) {
            Item item = menuBook.itemsMenuMap.get(selectMenu).get(num - 1);
            selectItem.setItem(item);
            print.addBasket(item);
            orderLevel = OrderLevel.ADD;
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel add(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.ADD;

        if (num == 1) {
            selectItem.setCount(selectItem.getCount() + 1);
            if (selectMenu == ItemMenu.COFFEE
                    || selectMenu == ItemMenu.TEA) {
                orderLevel = print.checkIce();
            } else {
                basket.add(selectItem);
                selectItem = new ItemBox();
                orderLevel = print.mainMenu(basket);
            }
        } else if (num == 2) {
            orderLevel = print.mainMenu(basket);
        } else if (num == 3) {
            orderLevel = print.checkCount();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel checkCount(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.COUNTOPTION;

        if (0 < num && num < 100) {
            int totalCount = selectItem.getCount() + num;
            if (totalCount < 100) {
                selectItem.setCount(totalCount);
                if (selectMenu == ItemMenu.COFFEE
                        || selectMenu == ItemMenu.TEA) {
                    orderLevel = print.checkIce();
                } else {
                    basket.add(selectItem);
                    selectItem = new ItemBox();
                    orderLevel = print.mainMenu(basket);
                }
            } else {
                System.out.println("수량이 초과되었습니다.");
                System.out.println();
                orderLevel = print.addBasket(selectItem.getItem());
            }
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel checkIce(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.ICEOPTION;

        if (num == 1) {
            selectItem.setHasIce(true);
            basket.add(selectItem);
            selectItem = new ItemBox();
            orderLevel = print.mainMenu(basket);
        } else if (num == 2) {
            selectItem.setHasIce(false);
            basket.add(selectItem);
            selectItem = new ItemBox();
            orderLevel = print.mainMenu(basket);
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel order(int num) throws WrongInputException, InterruptedException {
        OrderLevel orderLevel = OrderLevel.ORDER;

        if (num == 1) {
            basket.order();
            orderLevel = print.mainMenu(basket);
        } else if (num == 2) {
            orderLevel = print.mainMenu(basket);
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    // 장바구니 초기화
    public OrderLevel cancel(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.COUNTOPTION;

        if (num == 1) {
            basket.reset();
            orderLevel = print.mainMenu(basket);
        } else if (num == 2) {
            orderLevel = print.mainMenu(basket);
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }


}
