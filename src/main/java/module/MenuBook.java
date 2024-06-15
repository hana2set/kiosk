package module;

import menus.Drink;
import menus.Menu;
import error.WrongInputException;

import java.util.*;
import java.util.List;

public class MenuBook {

    private Menu[] menus = Menu.values();             //메뉴 목록
    private Map<Menu, List<Drink>> itemMap = new HashMap<>();    //메뉴 - 상세 메뉴 맵

    int titleLength; // 메뉴 제목 최대 크기

    public MenuBook() {
        Arrays.stream(Drink.values())
                .forEach(item -> {
                    itemMap.computeIfAbsent(item.getCategory(), k -> new ArrayList<>());
                    itemMap.get(item.getCategory()).add(item);
                });

        titleLength = Math.max(
                10,
                Arrays.stream(menus).mapToInt(m -> m.getName().length()).max().orElse(0));
    }

    public Menu getMenus(int num) throws WrongInputException {
        if (menus.length <= num) {
            throw new WrongInputException("잘못된 입력값입니다.");
        }
        return menus[num];
    }

    public List<Drink> get(Menu menu) {
        return itemMap.get(menu);
    }

    public Drink get(Menu menu, int num) throws WrongInputException {
        if (itemMap.get(menu).size() <= num) {
            throw new WrongInputException("잘못된 입력값입니다.");
        }
        return itemMap.get(menu).get(num);
    }

    public String getItemMenuTxt(Menu menu) {
        List<Drink> items =  itemMap.get(menu);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            sb.append(i + 1)
                    .append(". ").append(String.format("%-" + titleLength + "s", items.get(i).getName()))
                    .append(" | ₩ ").append(String.format("%5d", items.get(i).getPrice()))
                    .append(" | ").append(items.get(i).getDesc())
                    .append("\n");
        }
        return sb.toString();
    }

    public String getMenuTxt(boolean isBasketEmpty) {
        Menu[] menus = Menu.values();
        StringBuilder sb = new StringBuilder();

        sb.append("=======================================")
                .append("\n")
                .append("[ 메뉴 ]")
                .append("\n");

        int i = 0;
        for (; i < menus.length; i++) {
            if (menus[i].getCategory() != Menu.Category.ITEM) break;
            sb.append(i + 1)
                    .append(". ").append(String.format("%-" + titleLength + "s", menus[i].getName()))
                    .append(" | ").append(menus[i].getDesc())
                    .append("\n");
        }

        if (isBasketEmpty == false) {
            sb.append("\n")
                    .append("[ 주문확인 ]")
                    .append("\n");

            for (; i < menus.length; i++) {
                sb.append(i + 1)
                        .append(". ").append(String.format("%-" + titleLength + "s", menus[i].getName()))
                        .append(" | ").append(menus[i].getDesc())
                        .append("\n");
            }
        }

        return sb.toString();
    }

    public String getDrinkTxt(Drink drink) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%-" + titleLength + "s", drink.getName()))
                .append(" | ₩ ").append(String.format("%5d", drink.getPrice()))
                .append(" | ").append(drink.getDesc())
                .append("\n");

        return sb.toString();
    }
}
