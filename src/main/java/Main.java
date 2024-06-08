import constant.Color;
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

        // 데이터 입력받기
        Kiosk kiosk = new Kiosk();
        kiosk.start(); //scanner loop
    }
}
