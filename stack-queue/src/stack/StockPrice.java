package stack; /**
 * p.161 주식 가격
 *
 * 초 단위로 기록된 주식 가격이 담긴 배열 prices가 매개변수로 주어질 때, 가격이 떨어지지 않은 기간은 몇초인지 반환하는 solution() 함수를 완성하시오
 *
 * prices 의 각 가격은 1 이상 10,000 이하 자연수, prices의 길이는 2 이상 100,000 이하, 권장 시간 복잡도 O(N)
 *
 */

import java.util.*;

public class StockPrice {
    // 내 생각 : [1, 3, 9, 3, 5] 이런 배열에서 가격이 떨어지지 않은 기간 = 나보다 작은게 안나온 기간, O(N)이니 하나하나 볼 수는 없고
    // 맨 처음꺼를 계산 -> 다음꺼 또 계산 / 뒤에꺼부터 계산 -> 이어지면 그대로 쓰면 됨, 끊기면 끊긴 부분까지만 쓰면됨
    // 뒤에서부터 (최근꺼부터) 계산한다 -> 스택 활용 (전부 스택에 넣고 pop하면서 계산?)

    public static int solution(int[] prices) {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        boolean isGrowing = true;
        int growingPeriod = 0;
        int total = 0;

        for (int i=0; i<prices.length; i++) {
            ad.push(prices[i]);
        }

        for (int i=0; i<prices.length; i++) {
            int price = ad.pop();
            if (!ad.isEmpty() && price < ad.peek()) { // 주식이 하락하면
                isGrowing=false;
            }
            if (isGrowing) { // 주식이 상승중이면
                growingPeriod++;
            } else {
                total += growingPeriod;
                growingPeriod = 0;
                isGrowing = true;
            }
            total += growingPeriod;
        }

        return total;
    }

    // 떨어지지 않은 기간이 아니라 오른 기간 총 합을 구해버림... 그리고 스택 쓸 이유도 없었음

    /**
     *  발견 버그 3개 :
     *  if(!ad.isEmpty() && price < ad.peek()) <- 여기 isEmpty 조건 빼먹어서 마지막 peek() 호출 시 NoSuchElementException 발생함
     *  if(isGrowing) ... else { 여기에 isGrowing=true 안 넣어서 한번 false인 채로 끝나면 true로 복구 안됨 }
     *  루프 종료 시 isGrowing == true 인 채로 끝나면 마지막 growingPeriod가 solution에 안 더해짐 -> 조건문 밖에서 한번 더 더해야함 (어차피 하락하면 0이라)
     *
     *  그리고 solution 변수명이 별로임. total을 쓸 것
     *
     */

    // 오른 기간 총합 구하는 함수 (스택 안쓴 버전)
    public static int solution2(int[] prices) {
        int growingPeriod = 0;
        int total = 0;

        for (int i=1; i<prices.length; i++) {
            if(prices[i] >= prices[i-1]) {
                growingPeriod++;
            } else {
                total += growingPeriod;
                growingPeriod = 0;
            }
        }
        total += growingPeriod; // 마지막 구간 처리

        return total;
    }

    // 각 구간의 떨어지지 않은 기간 배열 구하기 (스택 사용)
    public static int[] solution3(int[] prices) {
        int n = prices.length;
        int[] answer = new int[n];

        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.push(0);

        for(int i=1; i<n; i++) {
            // 이전 주식 가격 (ad.peek())이 지금 비교중인 주식 가격보다 크면 = 주식 가격이 내려갔으면 pop하고 answer 배열에 기록
            while (!ad.isEmpty() && prices[i] < prices[ad.peek()]) {
                int j = ad.pop();
                answer[i] = i - j;
            }
            // 위 while문 탈출했으면 현재 비교중인 주식 가격 스택에 넣기
            ad.push(i);
        }
        // 모든 주식 가격 비교 끝났으면 스택에 남아있는 주식 가격들 pop 하면서 answer 기록
        while(!ad.isEmpty()) {
            int j = ad.pop();
            answer[j] = n - j - 1;
        }

        return answer;
    }
}
