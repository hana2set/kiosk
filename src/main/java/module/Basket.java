package module;

import dto.Item;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    public static final List<Item> basket = new ArrayList<>();          //장바구니

    public List<Item> getList() {
        return basket;
    }

    public void add(Item _selectItem) {
        Item selecttem = _selectItem;
        long existCount = basket.stream()
                .filter(item ->
                        item.getMenu() == selecttem.getMenu() && item.isHasIce() == selecttem.isHasIce()
                )
                .limit(1)
                .peek(item -> item.setCount(item.getCount() + selecttem.getCount()))
                .count();
        if (existCount == 0) {
            basket.add(selecttem);
        }
    }

    public void order() throws InterruptedException {
        Barista.makeDrink(basket);
        reset();
    }

    public void reset() {
        basket.clear();
    }

    public int size() {
        return basket.size();
    }

}
