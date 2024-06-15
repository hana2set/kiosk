package module;

import constant.Color;
import dto.Select;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Select> basket = new ArrayList<>();          //장바구니
    private Barista barista = new Barista();

    public void reset() {
        basket.clear();
    }

    public int size() {
        return basket.size();
    }

    public boolean isEmpty() {
        return basket.isEmpty();
    }

    public void add(Select item) {
        long existCount = basket.stream()
                .filter(select ->
                        select.getItem() == item.getItem() && select.isHasIce() == item.isHasIce()
                )
                .limit(1)
                .peek(select -> select.setCount(select.getCount() + item.getCount()))
                .count();
        if (existCount == 0) {
            basket.add(item);
        }
    }

    public void order() throws InterruptedException {
        barista.makeDrink(basket);
        reset();
    }

    public String getListTxt() {
        StringBuilder sb = new StringBuilder();

        int maxTitlelength = basket.stream().mapToInt(m -> m.getItem().getName().length()).max().orElse(0);
        int titleLength = Math.max(10, maxTitlelength);
        for (Select select : basket) {
            String iceText = select.isHasIce()
                    ? Color.ANSI_BLUE + "ICE" + Color.ANSI_RESET
                    : Color.ANSI_RED + "HOT" + Color.ANSI_RESET;
            sb.append(iceText).append(" ")
                    .append(String.format("%-" + titleLength + "s", select.getItem().getName()))
                    .append(" |").append(String.format("%3s", select.getCount())).append(" 개")
                    .append(" | ₩ ").append(String.format("%5d", select.calculateItemPrice()))
                    .append(" | ").append(select.getItem().getDesc())
                    .append("\n");
        }
        return sb.toString();
    }

    public int getTotalPrice() {
        return basket.stream().mapToInt(Select::calculateItemPrice).sum();
    }

}
