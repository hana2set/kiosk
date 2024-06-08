package module;

import constant.Color;
import dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<ItemInfo> basket = new ArrayList<>();          //장바구니
    private Barista barista = new Barista();

    public void reset() {
        basket.clear();
    }

    public int size() {
        return basket.size();
    }

    public void add(ItemInfo item) {
        long existCount = basket.stream()
                .filter(itemInfo ->
                        itemInfo.getItem() == item.getItem() && itemInfo.isHasIce() == item.isHasIce()
                )
                .limit(1)
                .peek(itemInfo -> itemInfo.setCount(itemInfo.getCount() + item.getCount()))
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
        for (ItemInfo itemInfo : basket) {
            String iceText = itemInfo.isHasIce()
                    ? Color.ANSI_BLUE + "ICE" + Color.ANSI_RESET
                    : Color.ANSI_RED + "HOT" + Color.ANSI_RESET;
            sb.append(iceText).append(" ")
                    .append(String.format("%-" + titleLength + "s", itemInfo.getItem().getName()))
                    .append(" |").append(String.format("%3s", itemInfo.getCount())).append(" 개")
                    .append(" | ₩ ").append(String.format("%5d", itemInfo.calculateItemPrice()))
                    .append(" | ").append(itemInfo.getItem().getDesc())
                    .append("\n");
        }
        return sb.toString();
    }

    public int getTotalPrice() {
        return basket.stream().mapToInt(ItemInfo::calculateItemPrice).sum();
    }

}
