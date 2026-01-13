/**
 * p.243 할인 행사
 * 회원이 원하는 제품들의 배열 want, 원하는 제품들의 수량을 나타내는 배열 number, 마트에서 할인하는 제품들의 목록을 나타내는 배열 discount가 있다.
 * 할인은 하루에 한 제품만 진행되며, 하루에 하나만 구매할 수 있다. 회원은 열흘동안 원하는 제품을 수량에 맞게 모두 연속으로 구매할 수 있는 날짜에 마트에 회원가입하려 한다.
 * 회원이 마트에 가입할 수 있는 날짜들의 총합을 반환하는 solution() 을 구현하시오.
 *
 * 가능한 날이 없으면 0을 반환, want와 number의 길이는 1~10, number의 총합은 10, discount의 길이는 10~100,000 이다.
 * 권장 시간복잡도 : O(N)
 *
 */
import java.util.*;
import java.io.*;
public class SaleFestival {
    public static int solution(String[] want, int[] number, String[] discount) {
        // 내 생각 : 회원이 원하는 제품을 순서에 관계 없이 원하는 수량만큼 모두 연속으로 구매해야 한다. discount에서 특정 단어들이 포함된 10개 길이의 구간을 모두 찾아야 한다.
        // 특정 단어를 중복이 있는 긴 배열에서 검색 -> hashMap 활용, key에는 상품명을, value에는 discount의 인덱스를 넣는다면?
        // 물품 a를 검색 -> 인덱스들이 나옴, 물품 b를 검색 -> 인덱스들이 나옴, ... , 나온 인덱스를 오름차순 정렬 후, 윈도우 방식으로 하나씩 검사
        // hashmap 만들기 100,000번, 윈도우 반복 최대 10^2 -> O(N) 범위 내

        // map은 중복값 저장하면 덮어씌워진다... 일단 List<Integer> 활용해서 위 방식대로 구현
        // 생각해보니 인덱스를 정렬하면 인덱스가 어떤 상품인지 알 수가 없음
        // 이 방법은 포기

        HashMap<String, List<Integer>> map = new HashMap<>();
        int index = 0;
        for(String d : discount) {
            if(map.containsKey(d)) {
                List<Integer> list = map.get(d);
                list.add(index++);
                map.put(d, list);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(index++);
                map.put(d, list);
            }
        }
        return 0;
    }

    public static int solution2(String[] want, int[] number, String[] discount) {
        // discount에서 특정 단어들이 포함된 10개 길이의 구간을 모두 찾아야 한다. hashmap<상품명, 인덱스> 넣고, 상품명 검색 -> 낮은 인덱스 부터 윈도우 방식 검사...
        HashMap<String, List<Integer>> map = new HashMap<>();
        int index = 0;
        for(String d : discount) {
            if(map.containsKey(d)) {
                List<Integer> list = map.get(d);
                list.add(index++);
                map.put(d, list);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(index++);
                map.put(d, list);
            }
        }

        return 0;
    }

    // 정답 : 두 hashmap 끼리 equals 검사가 가능하다는 점을 활용, 첫번째 맵에는 want와 number를 이용해 Map<상품명, 구매할 갯수> 를 저장하고,
    // 두번째 맵에는 특정 날짜부터 10일간 구매할 수 있는 품목들과 품목의 갯수를 저장한다.
    // 두 맵의 크기와 <키,밸류> 쌍이 하나도 빠짐없이 같으면 equals는 true를 반환하므로, answer++ (순서는 신경쓰지 않아도 됨)
    public static int solution3(String[] want, int[] number, String[] discount) {
        int answer = 0;

        HashMap<String, Integer> wantMap = new HashMap<>();
        for(int i=0; i<want.length; i++) {
            wantMap.put(want[i], number[i]);
        }

        for(int i=0; i<discount.length - 9; i++) {
            HashMap<String, Integer> discountMap = new HashMap<>();

            for(int j=i; j<i+10; j++) {
                discountMap.put(discount[i], discountMap.getOrDefault(discount[j], 0) + 1);
            }

            if(discountMap.equals(wantMap)) answer++;
        }
        return answer;
    }
}
