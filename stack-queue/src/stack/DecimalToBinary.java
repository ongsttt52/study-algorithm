package stack;

import java.util.Stack;

/**
 * p.149 10진수를 2진수로 변환하기
 * 10진수를 입력받아 2진수로 변환해 반환하는 solution() 함수 구현하기
 *
 * decimal은 1 이상 10억 미만의 자연수
 * 권장 시간 복잡도 : O(logN)
 */
class DecimalToBinary {
    public static String solution(long decimal) {
        // 10진수를 2진수로 변환하려면?? 10 -> 10 이하의 가장 큰 2의 n승 구한다 -> 10 - 2의 n승의 차를 구한다 -> 또 차를 구한다 -> ... -> 1 또는 0 이 될때까지 반복
        // 여기서 스택을 쓸 필요가 있나??
        // 10억 미만의 자연수 -> 연산 최대 33*32번 -> 시간 복잡도 O(N^2)??

        int current = 1;
        int i=0;
        while(decimal > current) {
            i++;
            current = current * 2;
            if(current > decimal) break;
        }

        String binary = "";

        while(current > 1) {
            i--;
            current = current / 2;
            if(decimal > current) {
                binary += "1";
                decimal = decimal - current;
            } else {
                binary += "0";
            }
        }
        return binary;
    }

    public static String solution2(long decimal) {
        if (decimal == 0) return "0";

        long current = 1;
        while (current * 2 <= decimal) {
            current *= 2;
        }

        StringBuilder binary = new StringBuilder();
        while (current >= 1) {
            if (decimal >= current) {
                binary.append("1");
                decimal -= current;
            } else {
                binary.append("0");
            }
            current /= 2;
        }
        return binary.toString();
    }

    // 스택 활용한 방식, 10진수를 2로 나눈 나머지를 나열한 후 거꾸로 하면 2진수로 변환됨을 활용
    public static String solution3(long decimal) {
        Stack<Long> stack = new Stack<>();

        // 13 넣으면 Stack에 1 0 1 1 -> pop 하면 1 1 0 1
        while(decimal > 0) {
            long a = decimal % 2;
            stack.push(a);
            decimal = decimal / 2;
        }

        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }
}

