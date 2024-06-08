package module;

import Menus.detail.Item;
import Menus.main.ItemMenu;
import Menus.main.OrderMenu;
import dto.ItemInfo;
import error.WrongInputException;

import java.util.*;

public class Kiosk {
    private Scanner sc = new Scanner(System.in);    //입력
    private Printer print = new Printer();          //출력

    private MenuBook menuBook = new MenuBook();     //메뉴
    private ItemMenu selectMenu;                    //선택된 상세 메뉴
    private ItemInfo selectItemInfo = new ItemInfo();     //선택된 상품

    private Basket basket = new Basket();            //장바구니


    public void start() {
        while (true) {
            try {
                getOrder();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력값입니다.");
                sc.next(); //
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("에러를 견디지 못하고 시스템이 종료됩니다.");
                //TODO error to file
                System.exit(0);
            }
        }
    }

    private void getOrder() throws WrongInputException, InterruptedException {
        print.menu(basket);
        int num = sc.nextInt();

        boolean isItemMenu = 0 < num && num <= menuBook.itemMenusSize();
        boolean isOrderMenu = basket.size() > 0 && num <= menuBook.itemMenusSize() + menuBook.orderMenusSize();

        if (isItemMenu) {
            getDetailMenu(menuBook.getItemMenus(num - 1));
        } else if (isOrderMenu) {
            getOrderMenu(menuBook.getOrderMenus(num - menuBook.itemMenusSize() - 1));
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }
    }

    private void getDetailMenu(ItemMenu menu) throws WrongInputException {
        selectMenu = menu;
        print.detailMenu(menu);
        int num = sc.nextInt();

        boolean isDetailMenu = 0 < num && num <= menuBook.get(selectMenu).size();

        if (isDetailMenu) {
            selectItemInfo.setItem(menuBook.get(selectMenu).get(num-1));
            addMenuInBasket(selectItemInfo.getItem());
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }
    }

    private void getOrderMenu(OrderMenu menu) throws InterruptedException, WrongInputException {
        switch (menu) {
            case ORDER -> order();
            case CANCEL -> orderCancel();
            default -> {
                System.out.println("키오스크를 종료합니다.");
                System.exit(0);
            }
        }
    }

    private void addMenuInBasket(Item menu) throws WrongInputException {
        print.addBasket(menu);
        int num = sc.nextInt();

        if (num == 1) {
            selectItemInfo.setCount(selectItemInfo.getCount() + 1);
            if (selectMenu == ItemMenu.COFFEE
                    || selectMenu == ItemMenu.TEA) {
                checkIce();
            } else {
                addBasket();
            }
        } else if (num == 2) {
            print.menu(basket);
        } else if (num == 3) {
            checkCount();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }
    }

    private void addBasket() {
        basket.add(selectItemInfo);

        selectItemInfo = new ItemInfo();
    }

    private void checkCount() throws WrongInputException {
        print.checkCount();
        int num = sc.nextInt();

        if ( 0 < num && num < 100) {
            int totalCount = selectItemInfo.getCount() + num;
            if (num < 100) {
                selectItemInfo.setCount(totalCount);
                if (selectMenu == ItemMenu.COFFEE
                        || selectMenu == ItemMenu.TEA) {
                    checkIce();
                } else {
                    addBasket();
                }
            } else {
                System.out.println("수량이 초과되었습니다.");
                System.out.println("");
                addMenuInBasket(selectItemInfo.getItem());
            }
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

    }

    private void checkIce() throws WrongInputException {
        print.checkIce();
        int num = sc.nextInt();

        if (num == 1) {
            selectItemInfo.setHasIce(true);
        } else if (num == 2) {
            selectItemInfo.setHasIce(false);
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        addBasket();

    }

    private void order() throws WrongInputException, InterruptedException {
        print.order(basket);
        int num = sc.nextInt();

        if (num == 2) {
            return;
        }

        if (num == 1) {
            basket.order();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }
    }

    private void orderCancel() throws WrongInputException {
        print.cancel();

        int num = sc.nextInt();

        if (num == 1) {
            basket.reset();
        } else if (num == 2) {

        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

    }

}
