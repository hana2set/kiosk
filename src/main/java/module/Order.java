package module;

import Items.*;
import Items.superobject.DetailMenu;
import Items.superobject.Menu;
import constant.Color;
import constant.OrderLevel;
import dto.Item;
import error.WrongInputException;

import java.util.*;

public class Order {
    Map<Menu, List<DetailMenu>> menuMap;            //메뉴 - 상세 메뉴 맵
    ItemMenu[] itemMenus = ItemMenu.values();       //메뉴 목록
    OrderMenu[] orderMenus = OrderMenu.values();    //명령 목록

    OrderLevel orderLevel = OrderLevel.MAIN;        //주문 계층
    Menu detailMenu;                                //선택된 상세 메뉴
    Item selectItem = new Item();                   //선택된 상품
    List<Item> basket = new ArrayList<>();          //장바구니

    public void getInputValue() {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                int num = sc.nextInt();

                System.out.println();
                System.out.println("=======================================");
                orderLevel = switch (orderLevel) {
                    case MAIN -> branchMain(num);
                    case DETAIL -> branchDetail(num);
                    case ADD -> branchAdd(num);
                    case CHECKCOUNT -> branchCheckCount(num);
                    case CHECKICE -> branchCheckIce(num);
                    case CANCEL -> branchCancel(num);
                    case ORDER -> branchOrder(num);
                };

            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력값입니다.");
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.exit(0);
            }
        }
    }

    public OrderLevel branchMain(int num) throws WrongInputException {
        OrderLevel orderLevel;
        if (0 < num && num <= itemMenus.length) {
            // 상품 메뉴
            orderLevel = getMenuDetail(itemMenus[num - 1]);
        } else if (basket.size() > 0 && num <= itemMenus.length + orderMenus.length) {
            // 주문 메뉴
            orderLevel = switch (orderMenus[num - itemMenus.length - 1]) {
                case ORDER -> getOrder();
                case CANCEL -> getCancel();
//                default -> {
//                    System.out.println("키오스크를 종료합니다.");
//                    System.exit(0);
//                    yield OrderLevel.MAIN;
//                }
            };
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel branchDetail(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.DETAIL;

        if (0 < num && num <= menuMap.get(detailMenu).size()) {
            DetailMenu selectMenu = menuMap.get(this.detailMenu).get(num - 1);
            selectItem.setMenu(selectMenu);
            getAddBasket(selectMenu);
            orderLevel = OrderLevel.ADD;
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel branchAdd(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.ADD;

        if (num == 1) {
            selectItem.setCount(selectItem.getCount() + 1);
            if (detailMenu == ItemMenu.COFFEE
                    || detailMenu == ItemMenu.TEA) {
                orderLevel = getCheckIce();
            } else {
                addBasket();
                orderLevel = getMainMenu();
            }
        } else if (num == 2) {
            orderLevel = getMainMenu();
        } else if (num == 3) {
            orderLevel = getCheckCount();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel branchCheckCount(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.CHECKCOUNT;

        if (0 < num && num < 100) {
            int totalCount = selectItem.getCount() + num;
            if (totalCount < 100) {
                selectItem.setCount(totalCount);
                if (detailMenu == ItemMenu.COFFEE
                        || detailMenu == ItemMenu.TEA) {
                    orderLevel = getCheckIce();
                } else {
                    addBasket();
                    orderLevel = getMainMenu();
                }
            } else {
                System.out.println("수량이 초과되었습니다.");
                System.out.println();
                orderLevel = getAddBasket(selectItem.getMenu());
            }
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel branchCheckIce(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.CHECKICE;

        if (num == 1) {
            selectItem.setHasIce(true);
            addBasket();
            orderLevel = getMainMenu();
        } else if (num == 2) {
            selectItem.setHasIce(false);
            addBasket();
            orderLevel = getMainMenu();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }



    public OrderLevel branchOrder(int num) throws WrongInputException, InterruptedException {
        OrderLevel orderLevel = OrderLevel.ORDER;

        if (num == 1) {
            Barista.makeDrink(basket);
            basket = new ArrayList<>(); //장바구니 초기화
            orderLevel = getMainMenu();
        } else if (num == 2) {
            orderLevel = getMainMenu();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }


    public OrderLevel branchCancel(int num) throws WrongInputException {
        OrderLevel orderLevel = OrderLevel.CHECKCOUNT;

        if (num == 1) {
            cancelBasket();
            orderLevel = getMainMenu();
        } else if (num == 2) {
            orderLevel = getMainMenu();
        } else {
            throw new WrongInputException("잘못된 입력값입니다.");
        }

        return orderLevel;
    }

    public OrderLevel getMainMenu() {
        System.out.println("[ 메뉴 ]");
        printMenuItem(itemMenus, 0);
        if (basket.size() > 0) {
            System.out.println();
        System.out.println("[ 주문확인 ]");
            printMenuItem(orderMenus, itemMenus.length);
        }

        return OrderLevel.MAIN;
    }
    public OrderLevel getMenuDetail(Menu menu) {
        System.out.println("[ 상세 메뉴 목록 ]");
        printDetailMenuItem(menuMap.get(menu), 0);
        detailMenu = menu;

        return OrderLevel.DETAIL;

    }

    public OrderLevel getAddBasket(DetailMenu menu) {
        printDetailMenuItem(menu);
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println();
        System.out.println("1. 확인       2. 취소       3.수량 선택");

        return OrderLevel.ADD;
    }

    public OrderLevel getCheckCount() {
        System.out.println("주문할 수량을 입력해주세요.(1-99)");

        return OrderLevel.CHECKCOUNT;
    }

    public OrderLevel getCheckIce() {
        System.out.println("얼음을 추가하시겠습니까? 추가요금이 500원 부가됩니다.");
        System.out.println();
        System.out.println("1. 추가       2. 추가 없음");

        return OrderLevel.CHECKICE;
    }

    public OrderLevel getOrder() {
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println(" [ 주문 목록 ] ");
        printBasketList(basket);
        System.out.println();
        System.out.println(" [ 총액 ] ");
        printBasketTotal(basket);
        System.out.println();
        System.out.println("1. 확인       2. 메뉴판");

        return OrderLevel.ORDER;
    }


    private void addBasket() {
        long existCount = basket.stream()
                .filter(item ->
                        item.getMenu() == selectItem.getMenu() && item.isHasIce() == selectItem.isHasIce()
                )
                .limit(1)
                .peek(item -> item.setCount(item.getCount() + selectItem.getCount()))
                .count();
        if (existCount == 0) {
            basket.add(selectItem);
        }
        selectItem = new Item();
    }


    public void cancelBasket() {
        basket = new ArrayList<>();
    }


    public OrderLevel getCancel() {
        System.out.println("주문을 취소하시겠습니까?");
        System.out.println();
        System.out.println("1. 확인       2. 취소");

        return OrderLevel.CANCEL;
    }

    public void printMenuItem(Menu[] menus, int startNo) {
        int maxTitlelength = Arrays.stream(menus).mapToInt(m -> m.getName().length()).max().orElse(0);
        int titleLength = Math.max(10, maxTitlelength);
        for (int i = 0; i < menus.length; i++) {
            System.out.println((startNo + i + 1) + ". " + String.format("%-" + titleLength + "s", menus[i].getName())+" | " + menus[i].getDesc());
        }
    }

    public void printDetailMenuItem(DetailMenu menu) {
        int maxTitlelength = menu.getName().length();
        int titleLength = Math.max(10, maxTitlelength);
            System.out.println(
                            String.format("%-" + titleLength + "s", menu.getName())
                            + " | ₩ " + String.format("%5d", menu.getPrice())
                            + " | " + menu.getDesc()
            );
    }

    public void printDetailMenuItem(List<DetailMenu> menus, int startNo) {
        int maxTitlelength = menus.stream().mapToInt(m -> m.getName().length()).max().orElse(0);
        int titleLength = Math.max(10, maxTitlelength);
        for (int i = 0; i < menus.size(); i++) {
            System.out.println(
                    (startNo + i + 1)
                            + ". " + String.format("%-" + titleLength + "s", menus.get(i).getName())
                            + " | ₩ " + String.format("%5d", menus.get(i).getPrice())
                            + " | " + menus.get(i).getDesc()
            );
        }
    }

    public void printBasketList(List<Item> basket) {
        int maxTitlelength = basket.stream().mapToInt(m -> m.getMenu().getName().length()).max().orElse(0);
        int titleLength = Math.max(10, maxTitlelength);
        for (Item item : basket) {
            String iceText = item.isHasIce()
                    ? Color.ANSI_BLUE + "ICE" + Color.ANSI_RESET
                    : Color.ANSI_RED + "HOT" + Color.ANSI_RESET;
            System.out.println(
                    iceText
                            + " " + String.format("%-" + titleLength + "s", item.getMenu().getName())
                            + " |" + String.format("%3s", item.getCount()) + " 개"
                            + " | ₩ " + String.format("%5d", item.calculateItemPrice())
                            + " | " + item.getMenu().getDesc()
            );
        }
    }

    public void printBasketTotal(List<Item> basket) {
        int totalPrice = basket.stream()
                .mapToInt(Item::calculateItemPrice).sum();
        System.out.println("₩ " + totalPrice);
    }

}
