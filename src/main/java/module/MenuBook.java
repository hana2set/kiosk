package module;

import Menus.detail.Item;
import Menus.main.ItemMenu;
import Menus.detail.Ade;
import Menus.detail.Coffee;
import Menus.main.Menu;
import Menus.detail.Tea;
import Menus.main.OrderMenu;

import java.util.*;

public class MenuBook {
    private List<String> menus = new ArrayList<>();
    private ItemMenu[] itemMenus = ItemMenu.values();                //메뉴 목록
    private OrderMenu[] orderMenus = OrderMenu.values();             //명령 목록

    public Map<Menu, List<Item>> itemsMenuMap = new HashMap<>();    //메뉴 - 상세 메뉴 맵

    public MenuBook() {
        Arrays.stream(Coffee.values())
                .forEach(item -> {
                    itemsMenuMap.computeIfAbsent(ItemMenu.COFFEE, k -> new ArrayList<>());
                    itemsMenuMap.get(ItemMenu.COFFEE).add(item);
                });

        Arrays.stream(Tea.values())
                .forEach(item -> {
                    itemsMenuMap.computeIfAbsent(ItemMenu.TEA, k -> new ArrayList<>());
                    itemsMenuMap.get(ItemMenu.TEA).add(item);
                });

        Arrays.stream(Ade.values())
                .forEach(item -> {
                    itemsMenuMap.computeIfAbsent(ItemMenu.ETC, k -> new ArrayList<>());
                    itemsMenuMap.get(ItemMenu.ETC).add(item);
                });

        
    }

    public ItemMenu getItemMenus(int num) {
        return itemMenus[num];
    }

    public OrderMenu getOrderMenus(int num) {
        return orderMenus[num];
    }

    public int itemMenusSize() {
        return itemMenus.length;
    }

    public int orderMenusSize() {
        return orderMenus.length;
    }

    public List<Item> get(Menu menu) {
        return itemsMenuMap.get(menu);
    }

    public String getItemMenuTxt(Menu menu, int startNo) {
        List<Item> items =  itemsMenuMap.get(menu);
        StringBuilder sb = new StringBuilder();

        int maxTitlelength = items.stream().mapToInt(m -> m.getName().length()).max().orElse(0);
        int titleLength = Math.max(10, maxTitlelength);
        for (int i = 0; i < items.size(); i++) {
            sb.append(startNo + i + 1)
                    .append(". ").append(String.format("%-" + titleLength + "s", items.get(i).getName()))
                    .append(" | ₩ ").append(String.format("%5d", items.get(i).getPrice()))
                    .append(" | ").append(items.get(i).getDesc())
                    .append("\n");
        }
        return sb.toString();
    }

    public String getItemMenuTxt(int startNo) {
        Menu[] menus = itemMenus;
        StringBuilder sb = new StringBuilder();

        int maxTitlelength = Arrays.stream(menus).mapToInt(m -> m.getName().length()).max().orElse(0);
        int titleLength = Math.max(10, maxTitlelength);
        for (int i = 0; i < menus.length; i++) {
            sb.append(startNo + i + 1)
                    .append(". ").append(String.format("%-" + titleLength + "s", menus[i].getName()))
                    .append(" | ").append(menus[i].getDesc())
                    .append("\n");
        }
        return sb.toString();
    }

    public String getOrderMenuTxt(int startNo) {
        Menu[] menus = orderMenus;
        StringBuilder sb = new StringBuilder();

        int maxTitlelength = Arrays.stream(menus).mapToInt(m -> m.getName().length()).max().orElse(0);
        int titleLength = Math.max(10, maxTitlelength);
        for (int i = 0; i < menus.length; i++) {
            sb.append(startNo + i + 1)
                    .append(". ").append(String.format("%-" + titleLength + "s", menus[i].getName()))
                    .append(" | ").append(menus[i].getDesc())
                    .append("\n");
        }
        return sb.toString();
    }

    public String getItemTxt(Item item) {
        StringBuilder sb = new StringBuilder();

        int maxTitlelength = item.getName().length();
        int titleLength = Math.max(10, maxTitlelength);
        sb.append(String.format("%-" + titleLength + "s", item.getName()))
                .append(" | ₩ ").append(String.format("%5d", item.getPrice()))
                .append(" | ").append(item.getDesc())
                .append("\n");

        return sb.toString();
    }
}
