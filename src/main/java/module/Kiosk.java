package module;

import menus.Drink;
import menus.Menu;
import dto.Select;
import error.WrongInputException;

import java.util.*;

public class Kiosk {
    private Scanner sc = new Scanner(System.in);    //입력
    private Printer print = new Printer();          //출력

    private MenuBook menuBook = new MenuBook();     //메뉴
    private Menu selectMenu;                        //선택된 상세 메뉴
    private Select select = new Select();           //선택된 상품

    private Basket basket = new Basket();           //장바구니


    public void start() {
        while (true) {
            try {
                getOrder();
            } catch (InputMismatchException e) {
                print.error("잘못된 입력값입니다.");
                sc.next(); // int 아닌값 제거
            } catch (WrongInputException e) {
                print.error(e.getMessage());
            } catch (Exception e) {
                print.error("에러를 견디지 못하고 시스템이 종료됩니다.");
                //TODO error to file
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    private void getOrder() throws WrongInputException, InterruptedException {
        print.menu(basket.isEmpty());
        int num = sc.nextInt();

        Menu menu = menuBook.getMenus(num - 1);;

        if (menu.getCategory() == Menu.Category.ITEM) {
            getDetailMenu(menu);
        } else {
            getOrderMenu(menu);
        }
    }

    private void getDetailMenu(Menu menu) throws WrongInputException {
        selectMenu = menu;
        print.detailMenu(menu);
        int num = sc.nextInt();

        Drink drink = menuBook.get(menu, num-1);

        select.setDrink(drink);
        addMenuInBasket(drink);
    }

    private void getOrderMenu(Menu menu) throws InterruptedException, WrongInputException {
        switch (menu) {
            case ORDER -> order();
            case CANCEL -> orderCancel();
            default -> {
                System.out.println("키오스크를 종료합니다.");
                System.exit(0);
            }
        }
    }

    private void addMenuInBasket(Drink drink) throws WrongInputException {
        print.addBasket(drink);
        int num = sc.nextInt();

        if (num == 1) {
            select.setCount(select.getCount() + 1);
            if (selectMenu == Menu.COFFEE
                    || selectMenu == Menu.TEA) {
                checkIce();
            } else {
                addBasket();
            }
        } else if (num == 2) {
            print.menu(basket.isEmpty());
        } else if (num == 3) {
            checkCount();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }
    }

    private void addBasket() {
        basket.add(select);

        select = new Select();
    }

    private void checkCount() throws WrongInputException {
        print.checkCount();
        int num = sc.nextInt();

        if ( 0 < num && num < 100) {
            int totalCount = select.getCount() + num;
            if (num < 100) {
                select.setCount(totalCount);
                if (selectMenu == Menu.COFFEE
                        || selectMenu == Menu.TEA) {
                    checkIce();
                } else {
                    addBasket();
                }
            } else {
                System.out.println("수량이 초과되었습니다.");
                System.out.println("");
                addMenuInBasket(select.getDrink());
            }
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

    }

    private void checkIce() throws WrongInputException {
        print.checkIce();
        int num = sc.nextInt();

        if (num == 1) {
            select.setHasIce(true);
        } else if (num == 2) {
            select.setHasIce(false);
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
            basket = new Basket();
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
