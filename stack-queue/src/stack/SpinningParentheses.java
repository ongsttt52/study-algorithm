package stack; /**
 * p.153 괄호 회전하기
 *
 * 다음 규칙을 지키는 문자열을 올바른 괄호 문자열이라고 정의
 * (). [], {} 는 모두 올바른 괄호 문자열. 만약 A가 올바른 괄호 문자열이라면, (A), [A], {A} 도 올바른 괄호 문자열. 예: ([])
 * 만약 A, B가 올바른 괄호 문자열이라면, AB도 올바른 괄호 문자열 예: (){[]}
 *
 * 대중소 괄호로 이루어진 문자열 s가 매개변수로 주어질 때, 이 s를 왼쪽으로 x칸 만큼 회전시켰을 때 s가 올바른 괄호 문자열이 되게 하는 x의 갯수를 반환하는 solution() 함수를 완성하시오.
 *
 * s의 길이는 1000 이하, 권장 시간 복잡도 O(N^2)
 */
import java.util.*;
import java.lang.*;

public class SpinningParentheses {
    public static int solution(String s) {
        // 내 생각 : 문자열을 x칸 만큼 회전시킨다 -> 1칸 이후 문자열 검사, 2칸 이후 문자열 검사, ...
        // 또는 맨 왼쪽 문자 하나 빼서 마지막에 넣고, 또 왼쪽 문자 빼서 마지막에 넣고, ... -> 스택의 push & pop과 유사함
        // push & pop 반복을 문자열 길이만큼 하고, 반복할 때마다 전부 검사..?

        // 힌트 : 닫힌 괄호를 처음 보는 순간 가장 마지막에 찾았던 같은 모양의 열린 괄호를 찾을 수 있어야 한다. -> 여기서 스택을 활용

        Stack<Character> stack1 = new Stack<>();
        Stack<Character> stack2 = new Stack<>();
        boolean isSame = true;
        int number = 0;

        // 1. 문자열 회전 반복문 만들기
        for(int i=0; i<s.length(); i++) {
                String forwardString = s.substring(i,s.length());
                String backwardString = s.substring(0,i);
                String spinnedString = forwardString + backwardString;

                for(int j=0; j<s.length(); j++) {
                    char c = spinnedString.charAt(j);
                    stack1.push(c);
                }
               for(int k=0; k<s.length(); k++) {
                   char c = stack1.pop();

                   if (!isOpenParen(c)) {
                       stack2.push(c);
                   } else {
                       char closed = stack2.pop();
                       isSame = isSameParen(c, closed);
                   }
               }
               if(isSame) number++;
        }
        return number;
    }

    // 열린 괄호 검사기
    private static boolean isOpenParen(char c) {
        return c == '(' || c == '{' || c == '[';
    }

    // 같은 괄호 검사기
    private static boolean isSameParen(char open,char closed) {
        return (open == '(' && closed == ')') ||
                (open == '{' && closed == '}') ||
                (open == '[' && closed == ']');
    }

    // 진짜 답
    public static int trueSolution(String s) {
        int count = 0;

        for(int i=0; i<s.length(); i++) {
            String rotated = s.substring(i) + s.substring(0,i);
            if(isValid(rotated)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isValid(String s) {
        // 열린 괄호 들어갈 스택 선언
        Stack<Character> stack = new Stack<>();

        // 문자열 s를 한글자씩 처리하는 반복문
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);

            // 열린 괄호면 스택에 push, 닫힌 괄호면 마지막 열린 괄호 pop 해서 비교
            if(isOpenParen(c)) {
                stack.push(c);
            } else {
                // 닫힌 괄호인데 스택이 비어있으면 실패
                if(stack.isEmpty()) return false;

                // 닫힌 괄호인데 마지막 열린 괄호와 모양 다르면 실패
                char open = stack.pop();
                if(isSameParen(open, c)) return false;
            }
        }
        // 마지막에 스택이 비어있어야 올바른 괄호 문자열
        return stack.isEmpty();
    }

    // 최적화) 문자열을 실제로 회전시키지 않고 인덱스로 처리하면 O(N²)에서 문자열 생성 비용을 줄일 수 있다.
    private static boolean isValid(String s, int offset) {
        Stack<Character> stack = new Stack<>();
        int len = s.length();

        for (int i = 0; i < len; i++) {
            char c = s.charAt((i + offset) % len);  // 회전을 인덱스로 처리
            // ...
        }
        return stack.isEmpty();
    }
}
