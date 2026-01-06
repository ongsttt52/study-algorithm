package stack;

import java.util.*;
import java.lang.*;

/**
 * p.158 짝지어 제거하기
 *
 * 문자열 s를 왼쪽부터 순회하면서 같은 문자가 연속으로 나올 경우 같은 두 문자를 제거하고 남은 문자를 이어붙인 후 다시 처음부터 순회, 문자열이 없어질 때까지 반복이 가능한지 구하시오.
 *
 * 문자열 s 최대 길이는 1,000,000, 권장 시간복잡도는 O(N)
 */
public class MatchAndRemove {
    public static  int solution(String s) {
        // 생각 : 같은 문자가 연속으로 나온다 = 현재 순회중인 문자와 '가장 최근의' 문자를 비교 = 스택 활용
        // 남은 문자를 이어붙이는 것도 스택 구조를 활용하면 구현할 필요 없음. 최대 길이가 1,000,000 이므로 이중 반복문 안됨

        Stack<Character> stack = new Stack<>();

        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);

            if(stack.isEmpty() || stack.peek() != c) {
                stack.push(c);
            } else {
                stack.pop();
            }
        }
        return stack.isEmpty() ? 1 : 0;
    }
}
