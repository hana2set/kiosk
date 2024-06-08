
## 키오스크 

* module
  * Kiosk - 주문
  * Print - 출력
  * Basket - 장바구니
  * MenuBook - 메뉴판
  * Barista - 제조
* Menus - 상품 정보 모음
  * ItemMenu, OrderMenu - 기본 메뉴 정보
  * Ade, Coffe, Tea - 상세 메뉴 정보

  
<br>

### 자체 평가
* Enum을 통해 메뉴 구성 - bad
  * Enum 클래스 자체를 변수로 받을 수 없어서 객체 관리가 어려움
  * 변경 시 여러 부분을 변경해야함
  * extends 사용 불가로, 부모클래스가 interface로 강제됨
  * -> 외부 파일을 읽어 파싱하는 방법 사용하는게 더 좋을듯
* OrderLevel을 통해 한곳에서 input값을 분리
  * 추상화된 주문 계층이 맞지않음 (주문 옵션 선택 중이나, 해당 위치임을 알 방법이 없음)
  * -> 현재 상태에서 객체를 분리하기 어려워서 SingleTon 패턴 사용 염두
* print를 할 때 orderlevel을 반환하는 것이 좋은 방법인지?
  * 종속성이 강제되 별로 좋지 못한 패턴으로 보임
  * -> 삭제
* scanner를 앞쪽에 배치하려하니 구조 흐름이 한눈에 안보임
 * -> 인지적 흐름에 따라 설계해야 가독성이 높아질 듯

<br>
<br>

<details>
<summary>기타 등등</summary>

## Intellij Lombok
    Go to File > Settings > Plugins
    Click on Browse repositories...
    Search for Lombok Plugin
    Click on Install plugin
    Restart IntelliJ IDEA

</details>

