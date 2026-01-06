package queue;

/**
 * p.209 카드 뭉치
 * 영어 단억 적힌 카드 뭉치 2개가 있다. 다음과 같은 규칙으로 카드에 적힌 단어들을 사용해 원하는 순서의 단어 배열을 만들 수 있는지 알고 싶다.
 * 원하는 카드 뭉치에서 카드를 순서대로 한 장씩 사용, 한번 사용한 카드는 다시 사용 x, 카드를 사용하지 않고 다음으로 넘어갈 수는 없음, 기존에 주어진 카드 뭉치의 단어 순서는 바꿀 수 없음
 * 예를 들어 첫번째 카드 뭉치에 [i, drink, water] , 두번째 카드 뭉치에 [want, to] 가 있고 goal이 [i, want, to, drink, water] 일 때 두 카드 뭉치로 goal을 만들 수 있으므로 true를 반환,
 * 첫번째 카드 뭉치가 [i, water, drink] , 두번째 카드 뭉치가 [want, to] 라면 카드를 순서대로 사용해 goal을 만들 수 없으므로 false를 반환하면 된다.
 *
 * 1 <= card1, card2의 길이 <= 10, 두 카드뭉치에는 서로 다른 단어만 있음, 2 <= goal의 길이 <= card1 + card2의 길이, goal의 원소는 두 카드뭉치에 있는 단어들로만 이루어져 있음
 */
import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.*;

public class CardBundle {
    public static boolean solution(String[] cards1, String[] cards2, String[] goal) {
        // 문제 요약 : 카드1, 카드2에서 '순서대로' 한장씩 사용 -> 맨 앞의 데이터를 다룸 -> 큐 활용 / 맨 앞의 카드 1,2가 goal 배열의 알맞은 순서에 들어갈 수 있는지 판단하는 문제
        // 구현 : 큐를 두개 만들고 peek()으로 카드 두개 조회 -> goal[i]에 들어갈 수 있는지 확인 -> goal[goal.length-1] 까지 전부 들어갔다면 true, 아니면 false
        Queue<String> q1 = new ArrayDeque<>();
        Queue<String> q2 = new ArrayDeque<>();

        for(String c1 : cards1) q1.add(c1);
        for(String c2 : cards2) q2.add(c2);

        for(int i=0; i<goal.length; i++) {
            if(q1.isEmpty() || q2.isEmpty()) return false;

            if(q1.peek() == goal[i]) {
                q1.poll();
            } else if(q2.peek() == goal[i]) {
                q2.poll();
            } else {
                return false;
            }
        }
        return true;
    }
    /**
     * 버그
     * 1. 오타: cards2를 q1에 넣음
     * for(String c1 : cards1) q1.add(c1);
     * for(String c2 : cards2) q1.add(c2);  // q2에 넣어야 함
     * for(String c2 : cards2) q2.add(c2);
     *
     * 2. 문자열 비교에 == 사용
     * if(q1.peek() == goal[i])  // 참조 비교 (주소)
     * if(q1.peek().equals(goal[i]))  // 값 비교
     * - ==는 같은 객체인지 비교하고, equals()는 내용이 같은지 비교합니다. 문자열 비교는 항상 equals()를 쓰세요. -> 주의하자
     *
     * 3. 조건문 로직 오류
     * if(q1.isEmpty() || q2.isEmpty()) return false;
     * - 한쪽 카드뭉치가 먼저 소진되어도 나머지로 goal을 완성할 수 있습니다.
     *
     * - 조건 삭제하고, peek() 전에 isEmpty() 체크 -> peek() 시 큐가 비어있으면 바로 런타임 에러 난다. isEmpty()를 사용할 때는 런타임 에러 나는 곳을 보호해준다는 느낌으로 쓰자.
     * if(!q1.isEmpty() && q1.peek().equals(goal[i])) {
     *     q1.poll();
     * } else if(!q2.isEmpty() && q2.peek().equals(goal[i])) {
     *     q2.poll();
     * } else {
     *     return false;
     * }
     */
}
