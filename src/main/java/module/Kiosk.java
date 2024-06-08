package module;

import constant.Pointer;
import error.WrongInputException;

import java.util.*;

public class Kiosk {
    private Pointer pointer = Pointer.MAIN;  //주문 계층 (화면 위치)
    private Order order = new Order();                //주문 클래스
    private Printer print = new Printer();            //출력 클래스

    public void start() {
        print.mainMenu();

        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println();
                System.out.println("=======================================");

                int num = sc.nextInt();
                pointer = switch (pointer) {
                    case MAIN -> order.main(num);
                    case DETAIL -> order.detail(num);
                    case ADD -> order.add(num);
                    case COUNTOPTION -> order.checkCount(num);
                    case ICEOPTION -> order.checkIce(num);
                    case CANCEL -> order.cancel(num);
                    case ORDER -> order.order(num);
                };

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

}
