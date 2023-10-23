import constant.Color;
import module.MenuBook;
import module.Kiosk;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        // 배너 생성
        // http://patorjk.com/software/taag  Font Name: Bloody
        try (BufferedReader br = new BufferedReader(new FileReader("banner.txt"))) {
            String line;
            System.out.println(Color.ANSI_RED);
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println(Color.ANSI_RESET);
        } catch (IOException e) {
            //banner.txt 없습니다
        }

        // 메뉴판 초기화
        MenuBook init = new MenuBook();
        init.initMenuMap();

        // 메뉴화면 띄우기
        // 데이터 입력받기
        Kiosk kiosk = new Kiosk();
        kiosk.getMainMenu();
        kiosk.getInputValue(); //scanner loop
    }
}
