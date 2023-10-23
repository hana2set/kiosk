package module;

import Menus.detail.DetailMenu;
import Menus.main.ItemMenu;
import Menus.detail.Ade;
import Menus.detail.Coffee;
import Menus.main.Menu;
import Menus.detail.Tea;

import java.util.*;

public class MenuBook {
    public static final Map<Menu, List<DetailMenu>> detailMap = new HashMap<>();            //메뉴 - 상세 메뉴 맵

    public void initMenuMap() {
        Arrays.stream(Coffee.values())
                .forEach(item -> {
                    if (detailMap.get(ItemMenu.COFFEE) == null) {
                        detailMap.put(ItemMenu.COFFEE, new ArrayList<>());
                    }
                    detailMap.get(ItemMenu.COFFEE).add(item);
                });

        Arrays.stream(Tea.values())
                .forEach(item -> {
                    if (detailMap.get(ItemMenu.TEA) == null) {
                        detailMap.put(ItemMenu.TEA, new ArrayList<>());
                    }
                    detailMap.get(ItemMenu.TEA).add(item);
                });

        Arrays.stream(Ade.values())
                .forEach(item -> {
                    if (detailMap.get(ItemMenu.ETC) == null) {
                        detailMap.put(ItemMenu.ETC, new ArrayList<>());
                    }
                    detailMap.get(ItemMenu.ETC).add(item);
                });
    }

}
