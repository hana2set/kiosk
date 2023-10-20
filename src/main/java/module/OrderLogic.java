package module;

import Menus.main.ItemMenu;
import Menus.main.OrderMenu;
import Menus.detail.DetailMenu;
import Menus.main.Menu;
import dto.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderLogic {

    Map<Menu, List<DetailMenu>> menuMap;            //메뉴 - 상세 메뉴 맵
    ItemMenu[] itemMenus = ItemMenu.values();       //메뉴 목록
    OrderMenu[] orderMenus = OrderMenu.values();    //명령 목록

    Menu detailMenu;                                //선택된 상세 메뉴
    Item selectItem = new Item();                   //선택된 상품
    List<Item> basket = new ArrayList<>();          //장바구니


}
